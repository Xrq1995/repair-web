package cn.edu.ahu.repairbackend.controller;

import cn.edu.ahu.repairbackend.annotation.ParamCheck;
import cn.edu.ahu.repairbackend.entity.RepairCurEntity;
import cn.edu.ahu.repairbackend.entity.RepairLogEntity;
import cn.edu.ahu.repairbackend.entity.UserInfoEntity;
import cn.edu.ahu.repairbackend.repository.RepairCurRepository;
import cn.edu.ahu.repairbackend.repository.RepairLogRepository;
import cn.edu.ahu.repairbackend.repository.UserInfoRepository;
import cn.edu.ahu.repairbackend.util.ClassReflection;
import cn.edu.ahu.repairbackend.util.ResultMsg;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Author: WangMiao
 * Date: 2018/5/23
 * Time: 21:08
 * Created with IntelliJ IDEA
 * Description: 修缮服务部控制类
 */
@RestController
@RequestMapping(value = "/distribute")
public class DistributeController {

    private final UserInfoRepository userInfoRepository;
    private final RepairCurRepository repairCurRepository;
    private final RepairLogRepository repairLogRepository;

    public DistributeController(UserInfoRepository userInfoRepository, RepairCurRepository repairCurRepository, RepairLogRepository repairLogRepository) {
        this.userInfoRepository = userInfoRepository;
        this.repairCurRepository = repairCurRepository;
        this.repairLogRepository = repairLogRepository;
    }

    /**
     * 获得所有的维修工信息(按工种分类)
     * @return      后台回应封装(HashMap)
     */
    @RequestMapping(value = "/allWorkers", method = RequestMethod.GET)
    public ResultMsg getAllWorkersInfo() {
        HashMap<String, List<UserInfoEntity>> hashMap = new HashMap<>();
        List<UserInfoEntity> userInfoEntityList = userInfoRepository.findAllByCollege("修缮服务部");

        List<String> typeName = new ArrayList<>();
        /*遍历查询集合，将相同type的实体放在HashMap中同一个key对应的值内(聚类)*/
        for (UserInfoEntity userInfoEntity : userInfoEntityList) {
            String type = userInfoEntity.getType();
            if (type != null && typeName.contains(type)) {
                List<UserInfoEntity> userInfoEntityListExist = hashMap.get(type);
                userInfoEntityListExist.add(userInfoEntity);
                hashMap.put(type, userInfoEntityListExist);
            } else {
                typeName.add(type);
                List<UserInfoEntity> userInfoEntityListTemp = new ArrayList<>();
                userInfoEntityListTemp.add(userInfoEntity);
                hashMap.put(type, userInfoEntityListTemp);
            }
        }

        return new ResultMsg().success(hashMap);
    }

    /**
     * 获取所有当前已确认报修信息接口
     * @return  回应信息封装
     */
    @RequestMapping(value = "/getAllRepairCurConfirm", method = RequestMethod.GET)
    public ResultMsg getAllRepairCurConfirm() {
        return new ResultMsg().success(repairCurRepository.findByRepairStatus("已确认"));
    }

    /**
     * 获取所有当前已派发报修信息接口
     * @return  回应信息封装
     */
    @RequestMapping(value = "/getAllRepairCurDistribute", method = RequestMethod.GET)
    public ResultMsg getAllRepairCurDistribute(@ParamCheck String messengerName, @ParamCheck String messengerNumber) {
        UserInfoEntity userInfoEntity = userInfoRepository.findByNumberAndName(messengerNumber, messengerName);

        if (Objects.equals(userInfoEntity.getRole(), "维修工") || Objects.equals(userInfoEntity.getRole(), "班长")) {
            return new ResultMsg().success(repairCurRepository.findByRepairNumberAndRepairStatus(messengerNumber, "已派发"));
        } else {
            return new ResultMsg().success(repairCurRepository.findByRepairStatus("已派发"));
        }
    }

    /**
     * 获取所有当前已完成报修信息接口
     * @return  回应信息封装
     */
    @RequestMapping(value = "/getAllRepairFinish", method = RequestMethod.GET)
    public ResultMsg getAllRepairFinish(@ParamCheck String messengerName, @ParamCheck String messengerNumber) {
        UserInfoEntity userInfoEntity = userInfoRepository.findByNumberAndName(messengerNumber, messengerName);

        if (Objects.equals(userInfoEntity.getRole(), "维修工") || Objects.equals(userInfoEntity.getRole(), "班长")) {
            return new ResultMsg().success(repairLogRepository.findByRepairNumberAndRepairStatus(messengerNumber, "已完成"));
        } else {
            return new ResultMsg().success(repairLogRepository.findByRepairStatus("已完成"));
        }
    }

    /**
     * 派发当前报修信息接口
     * @param id                    报修信息id
     * @param repairOrderNumber     订单号
     * @param messengerName         信息员名字
     * @param messengerNumber       信息员工号
     * @return                      回应信息封装
     */
    @Transactional
    @RequestMapping(value = "/distributeRepairCur", method = RequestMethod.GET)
    public ResultMsg confirmRepairCur(@ParamCheck Integer id, @ParamCheck String repairOrderNumber,
                                      @ParamCheck String messengerName, @ParamCheck String messengerNumber,
                                      @ParamCheck String repairName, @ParamCheck String repairNumber) {
        Date date = new Date();
        RepairCurEntity repairCurEntity = repairCurRepository.findByIdAndRepairOrderNumber(id, repairOrderNumber);
        if (repairCurEntity.getRepairStatus() != null && Objects.equals(repairCurEntity.getRepairStatus(), "已确认")) {
            repairCurEntity.setRepairStatus("已派发");
            repairCurEntity.setDistributeTime(date);
            repairCurEntity.setUpdateTime(date);
            repairCurEntity.setMessengerName(messengerName);
            repairCurEntity.setMessengerNumber(messengerNumber);
            repairCurEntity.setRepairNumber(repairNumber);
            repairCurEntity.setRepairName(repairName);
            repairCurRepository.save(repairCurEntity);

            return new ResultMsg().success(repairCurRepository.findByRepairStatus("已确认"));
        } else {
            return new ResultMsg().failure("当前报修单状态异常，请刷新后重试");
        }

    }

    /**
     * 撤回订单状态接口
     * @param status            当前状态
     * @param id                订单id
     * @param repairOrderNumber 订单号
     * @param messengerName     修缮操作员名字
     * @param messengerNumber   修缮操作员工号
     * @return                  回应信息封装
     * @throws Exception        实体类反射异常
     */
    @Transactional
    @RequestMapping(value = "/rollBack")
    public ResultMsg rollBackDistribute(@ParamCheck String status, @ParamCheck Integer id,
                                        @ParamCheck String repairOrderNumber, @ParamCheck String messengerName,
                                        @ParamCheck String messengerNumber) throws Exception {
        Date date = new Date();

        if (Objects.equals(status, "已确认")) {
            RepairCurEntity repairCurEntity = repairCurRepository.findByIdAndRepairOrderNumber(id, repairOrderNumber);
            if (repairCurEntity.getRepairStatus() != null &&
                    Objects.equals(repairCurEntity.getRepairStatus(), "已确认")) {
                repairCurEntity.setRepairStatus("待处理");
                repairCurEntity.setConfirmTime(null);
                repairCurEntity.setUpdateTime(date);
                repairCurEntity.setMessengerName(messengerName);
                repairCurEntity.setMessengerNumber(messengerNumber);

                repairCurRepository.save(repairCurEntity);
                return new ResultMsg().success(repairCurRepository.findByRepairStatus("已确认"));
            } else {
                return new ResultMsg().failure("当前报修单状态异常，请刷新后重试");
            }
        } else if (Objects.equals(status, "已派发")) {
            RepairCurEntity repairCurEntity = repairCurRepository.findByIdAndRepairOrderNumber(id, repairOrderNumber);
            if (repairCurEntity.getRepairStatus() != null &&
                    Objects.equals(repairCurEntity.getRepairStatus(), "已派发")) {
                repairCurEntity.setRepairStatus("已确认");
                repairCurEntity.setUpdateTime(date);
                repairCurEntity.setDistributeTime(null);
                repairCurEntity.setMessengerName(messengerName);
                repairCurEntity.setMessengerNumber(messengerNumber);
                repairCurEntity.setRepairName(null);
                repairCurEntity.setRepairNumber(null);
                repairCurRepository.save(repairCurEntity);

                return new ResultMsg().success(repairCurRepository.findByRepairStatus("已派发"));
            } else {
                return new ResultMsg().failure("当前报修单状态异常，请刷新后重试");
            }
        } else {
            RepairLogEntity repairLogEntity = repairLogRepository.findByIdAndRepairOrderNumber(id, repairOrderNumber);
            if (repairLogEntity.getRepairStatus() != null &&
                    Objects.equals(repairLogEntity.getRepairStatus(), "已完成")) {
                RepairCurEntity repairCurEntity = new RepairCurEntity();
                ClassReflection.reflectionAttr(repairLogEntity, repairCurEntity);
                repairCurEntity.setRepairStatus("已派发");
                repairCurEntity.setUpdateTime(date);
                repairCurEntity.setFinishTime(null);
                repairCurEntity.setMessengerName(messengerName);
                repairCurEntity.setMessengerNumber(messengerNumber);

                repairCurRepository.save(repairCurEntity);
                repairLogRepository.delete(repairLogEntity);

                UserInfoEntity userInfoEntity = userInfoRepository.findByNumberAndName(messengerNumber, messengerName);
                if (Objects.equals(userInfoEntity.getRole(), "维修工") || Objects.equals(userInfoEntity.getRole(), "班长")) {
                    return new ResultMsg().success(repairLogRepository.findByRepairNumberAndRepairStatus(messengerNumber, "已完成"));
                } else {
                    return new ResultMsg().success(repairLogRepository.findByRepairStatus("已完成"));
                }
            } else {
                return new ResultMsg().failure("当前报修单状态异常，请刷新后重试");
            }
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
            repairLogEntity.setUpdateTime(date);
            repairLogEntity.setFinishTime(date);
            repairLogEntity.setRepairStatus("已完成");
            repairLogEntity.setMessengerName(messengerName);
            repairLogEntity.setMessengerNumber(messengerNumber);

            repairLogRepository.save(repairLogEntity);
            repairCurRepository.delete(repairCurEntity);
        } else {
            return new ResultMsg().failure("当前报修单状态异常，请刷新后重试");
        }
        return new ResultMsg().success(repairCurRepository.findByRepairStatus("已派发"));
    }

}
