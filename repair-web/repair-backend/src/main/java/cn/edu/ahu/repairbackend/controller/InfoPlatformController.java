package cn.edu.ahu.repairbackend.controller;

import cn.edu.ahu.repairbackend.annotation.ParamCheck;
import cn.edu.ahu.repairbackend.entity.RepairCurEntity;
import cn.edu.ahu.repairbackend.entity.RepairLogEntity;
import cn.edu.ahu.repairbackend.entity.UserInfoEntity;
import cn.edu.ahu.repairbackend.json.IdAndRepairOrderNumberJson;
import cn.edu.ahu.repairbackend.json.InfoPlatformJson;
import cn.edu.ahu.repairbackend.repository.RepairCurRepository;
import cn.edu.ahu.repairbackend.repository.RepairLogRepository;
import cn.edu.ahu.repairbackend.repository.UserInfoRepository;
import cn.edu.ahu.repairbackend.util.ClassReflection;
import cn.edu.ahu.repairbackend.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Author: WangMiao
 * Date: 2018/4/7
 * Time: 16:14
 * Created with IntelliJ IDEA
 * Description: 信息平台页面controller
 */
@RestController
@RequestMapping(value = "/infoPlatform")
public class InfoPlatformController {

    private final UserInfoRepository userInfoRepository;
    private final RepairCurRepository repairCurRepository;
    private final RepairLogRepository repairLogRepository;

    @Autowired
    public InfoPlatformController(UserInfoRepository userInfoRepository, RepairCurRepository repairCurRepository, RepairLogRepository repairLogRepository) {
        this.userInfoRepository = userInfoRepository;
        this.repairCurRepository = repairCurRepository;
        this.repairLogRepository = repairLogRepository;
    }

    /**
     * 获取所有当前待处理报修信息接口
     * @return  回应信息封装
     */
    @RequestMapping(value = "/getAllRepairCur", method = RequestMethod.GET)
    public ResultMsg getAllRepairCur() {
        return new ResultMsg().success(repairCurRepository.findByRepairStatus("待处理"));
    }

    /**
     * 编辑当前报修信息接口
     * @param infoPlatformJson  信息平台编辑信息
     * @return                  回应信息封装
     */
    @Transactional
    @RequestMapping(value = "/editRepairCur", method = RequestMethod.POST)
    public ResultMsg confirmRepairCurById(@RequestBody InfoPlatformJson infoPlatformJson) {
        //查询该条信息并修改
        RepairCurEntity repairCurEntity = infoPlatformJson.editConvert(repairCurRepository.findByIdAndRepairOrderNumber
                (infoPlatformJson.getId(), infoPlatformJson.getRepairOrderNumber()));

        /*保存手机号*/
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setTel(repairCurEntity.getTel());
        userInfoRepository.save(userInfoEntity);
        //向数据库保存实体信息
        if (repairCurEntity.getRepairStatus() != null && Objects.equals(repairCurEntity.getRepairStatus(), "待处理")) {
            repairCurRepository.save(repairCurEntity);
        } else {
            return new ResultMsg().failure("当前报修单状态异常，请刷新后重试");
        }
        return new ResultMsg().success(repairCurRepository.findByRepairStatus("待处理"));
    }

    /**
     * 确认当前报修信息接口
     * @param id                    报修信息id
     * @param repairOrderNumber     订单号
     * @param messengerName         信息员名字
     * @param messengerNumber       信息员工号
     * @return                      回应信息封装
     */
    @Transactional
    @RequestMapping(value = "confirmRepairCur", method = RequestMethod.GET)
    public ResultMsg confirmRepairCur(@ParamCheck Integer id, @ParamCheck String repairOrderNumber,
                                      @ParamCheck String messengerName, @ParamCheck String messengerNumber) {
        Date date = new Date();
        RepairCurEntity repairCurEntity = repairCurRepository.findByIdAndRepairOrderNumber(id, repairOrderNumber);
        if (repairCurEntity.getRepairStatus() != null && Objects.equals(repairCurEntity.getRepairStatus(), "待处理")) {
            repairCurEntity.setRepairStatus("已确认");
            repairCurEntity.setConfirmTime(date);
            repairCurEntity.setUpdateTime(date);
            repairCurEntity.setMessengerName(messengerName);
            repairCurEntity.setMessengerNumber(messengerNumber);
            repairCurRepository.save(repairCurEntity);
        } else {
            return new ResultMsg().failure("当前报修单状态异常，请刷新后重试");
        }

        return new ResultMsg().success(repairCurRepository.findByRepairStatus("待处理"));
    }

    /**
     * 取消当前报修信息接口
     * @param status            当前状态
     * @param id                订单id
     * @param repairOrderNumber 订单号
     * @param messengerName     信息员名字
     * @param messengerNumber   信息员工号
     * @return                  回应信息封装
     * @throws Exception        实体类反射异常
     */
    @Transactional
    @RequestMapping(value = "/cancelRepairCur", method = RequestMethod.GET)
    public ResultMsg cancelRepairCur(@ParamCheck String status, @ParamCheck Integer id,
                                     @ParamCheck String repairOrderNumber, @ParamCheck String messengerName,
                                     @ParamCheck String messengerNumber) throws Exception {
        Date date = new Date();
        RepairCurEntity repairCurEntity = repairCurRepository.findByIdAndRepairOrderNumber(id, repairOrderNumber);
        RepairLogEntity repairLogEntity = new RepairLogEntity();

        if (repairCurEntity.getRepairStatus() != null &&
                (Objects.equals(repairCurEntity.getRepairStatus(), "待处理")
                        || Objects.equals(repairCurEntity.getRepairStatus(), "已确认"))) {
            ClassReflection.reflectionAttr(repairCurEntity, repairLogEntity);
            repairLogEntity.setRepairStatus("已取消");
            repairLogEntity.setUpdateTime(date);
            repairLogEntity.setMessengerName(messengerName);
            repairLogEntity.setMessengerNumber(messengerNumber);

            repairLogRepository.save(repairLogEntity);
            repairCurRepository.delete(repairCurEntity);
        } else {
            return new ResultMsg().failure("当前报修单状态异常，请刷新后重试");
        }
        if (Objects.equals(status, "待处理")) {
            return new ResultMsg().success(repairCurRepository.findByRepairStatus("待处理"));
        } else {
            return new ResultMsg().success(repairCurRepository.findByNotRepairStatus("待处理"));
        }
    }

    /**
     * 批量确认当前报修信息接口
     * @param idAndRepairOrderNumberJsonList    报修信息集合
     * @return                                  回应信息封装
     */
    @Transactional
    @RequestMapping(value = "/confirmRepairCurList", method = RequestMethod.POST)
    public ResultMsg confirmRepairCurList(@ParamCheck @RequestBody List<IdAndRepairOrderNumberJson> idAndRepairOrderNumberJsonList) {
        Date date = new Date();
        idAndRepairOrderNumberJsonList.forEach(e -> {
            RepairCurEntity repairCurEntity = repairCurRepository.findByIdAndRepairOrderNumber(e.getId(), e.getRepairOrderNumber());
            if (repairCurEntity.getRepairStatus() != null &&
                    Objects.equals(repairCurEntity.getRepairStatus(), "待处理")) {
                repairCurEntity.setRepairStatus("已确认");
                repairCurEntity.setConfirmTime(date);
                repairCurEntity.setUpdateTime(date);
                repairCurEntity.setMessengerName(e.getMessengerName());
                repairCurEntity.setMessengerNumber(e.getMessengerNumber());
                repairCurRepository.save(repairCurEntity);
            }
        });

        return new ResultMsg().success(repairCurRepository.findByRepairStatus("待处理"));
    }

    /**
     * 获取所有当前已确认报修信息接口
     * @return  回应信息封装
     */
    @RequestMapping(value = "/getAllRepairCurConfirm", method = RequestMethod.GET)
    public ResultMsg getAllRepairCurConfirm() {
        return new ResultMsg().success(repairCurRepository.findByNotRepairStatus("待处理"));
    }

    /**
     * 获取所有历史报修信息接口
     * @return  回应信息封装
     */
    @RequestMapping(value = "/getAllRepairLog", method = RequestMethod.GET)
    public ResultMsg getAllRepairLog() {
        return new ResultMsg().success(repairLogRepository.findAll());
    }

    /**
     * 撤回报修信息状态接口
     * @param status            当前状态
     * @param id                订单id
     * @param repairOrderNumber 订单号
     * @param messengerName     信息员名字
     * @param messengerNumber   信息员工号
     * @return                  回应信息封装
     * @throws Exception        实体类反射异常
     */
    @Transactional
    @RequestMapping(value = "/rollBackRepairInfo", method = RequestMethod.GET)
    public ResultMsg rollBackRepairInfo(@ParamCheck String status, @ParamCheck Integer id,
                                        @ParamCheck String repairOrderNumber, @ParamCheck String messengerName,
                                        @ParamCheck String messengerNumber) throws Exception {
        Date date = new Date();

        if (Objects.equals(status, "已取消")) {
            RepairLogEntity repairLogEntity = repairLogRepository.findByIdAndRepairOrderNumber(id, repairOrderNumber);
            if (repairLogEntity.getRepairStatus() != null &&
                    Objects.equals(repairLogEntity.getRepairStatus(), "已取消")) {
                RepairCurEntity repairCurEntity = new RepairCurEntity();
                ClassReflection.reflectionAttr(repairLogEntity, repairCurEntity);
                repairCurEntity.setRepairStatus("待处理");
                repairCurEntity.setUpdateTime(date);
                repairCurEntity.setConfirmTime(null);
                repairCurEntity.setMessengerName(messengerName);
                repairCurEntity.setMessengerNumber(messengerNumber);

                repairCurRepository.save(repairCurEntity);
                repairLogRepository.delete(repairLogEntity);

                return new ResultMsg().success(repairLogRepository.findAll());
            } else {
                return new ResultMsg().failure("当前报修单状态异常，请刷新后重试");
            }
        } else {
            RepairCurEntity repairCurEntity = repairCurRepository.findByIdAndRepairOrderNumber(id, repairOrderNumber);
            if (repairCurEntity.getRepairStatus() != null &&
                    Objects.equals(repairCurEntity.getRepairStatus(), "已确认")) {
                repairCurEntity.setRepairStatus("待处理");
                repairCurEntity.setUpdateTime(date);
                repairCurEntity.setConfirmTime(null);
                repairCurEntity.setMessengerName(messengerName);
                repairCurEntity.setMessengerNumber(messengerNumber);

                repairCurRepository.save(repairCurEntity);
            } else {
                return new ResultMsg().failure("当前报修单状态异常，请刷新后重试");
            }

            return new ResultMsg().success(repairCurRepository.findByNotRepairStatus("待处理"));
        }
    }

    /**
     * 完成报修信息接口
     * @param id                订单id
     * @param repairOrderNumber 订单号
     * @param messengerName     信息员名字
     * @param messengerNumber   信息员工号
     * @return                  回应信息封装
     */
    @Transactional
    @RequestMapping(value = "/finishRepairCur", method = RequestMethod.GET)
    public ResultMsg finishRepairCur(@ParamCheck Integer id, @ParamCheck String repairOrderNumber, @ParamCheck String messengerName, @ParamCheck String messengerNumber) throws Exception {
        Date date = new Date();
        RepairCurEntity repairCurEntity = repairCurRepository.findByIdAndRepairOrderNumber(id, repairOrderNumber);
        if (repairCurEntity.getRepairStatus() != null &&
                Objects.equals(repairCurEntity.getRepairStatus(), "已派发")) {
            RepairLogEntity repairLogEntity = new RepairLogEntity();
            ClassReflection.reflectionAttr(repairCurEntity, repairLogEntity);
            repairLogEntity.setRepairStatus("已完成");
            repairLogEntity.setUpdateTime(date);
            repairLogEntity.setFinishTime(date);
            repairLogEntity.setMessengerName(messengerName);
            repairLogEntity.setMessengerNumber(messengerNumber);

            repairLogRepository.save(repairLogEntity);
            repairCurRepository.delete(repairCurEntity);
        } else {
            return new ResultMsg().failure("当前报修单尚未完成");
        }
        return new ResultMsg().success(repairCurRepository.findByNotRepairStatus("待处理"));
    }

}
