package cn.edu.ahu.repairbackend.controller;

import cn.edu.ahu.repairbackend.annotation.ParamCheck;
import cn.edu.ahu.repairbackend.domain.RepairInfo;
import cn.edu.ahu.repairbackend.entity.RepairCurEntity;
import cn.edu.ahu.repairbackend.entity.RepairLogEntity;
import cn.edu.ahu.repairbackend.entity.UserInfoEntity;
import cn.edu.ahu.repairbackend.exception.ExceptionManager;
import cn.edu.ahu.repairbackend.json.RepairInfoJson;
import cn.edu.ahu.repairbackend.repository.RepairCurRepository;
import cn.edu.ahu.repairbackend.repository.RepairLogRepository;
import cn.edu.ahu.repairbackend.repository.UserInfoRepository;
import cn.edu.ahu.repairbackend.util.ClassReflection;
import cn.edu.ahu.repairbackend.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Objects;

/**
 * Author: WangMiao
 * Date: 2018/1/17
 * Time: 14:04
 * Created with IntelliJ IDEA
 * Description: 报修信息控制类
 */
@RestController
@RequestMapping(value = "/repairInfo")
public class RepairInfoController {

    private final ExceptionManager exceptionManager;
    private final RepairCurRepository repairCurRepository;
    private final RepairLogRepository repairLogRepository;
    private final UserInfoRepository userInfoRepository;


    @Autowired
    public RepairInfoController(ExceptionManager exceptionManager, RepairCurRepository repairCurRepository, RepairLogRepository repairLogRepository, UserInfoRepository userInfoRepository) {
        this.exceptionManager = exceptionManager;
        this.repairCurRepository = repairCurRepository;
        this.repairLogRepository = repairLogRepository;
        this.userInfoRepository = userInfoRepository;
    }

    /**
     * 提交报修信息接口
     * @param multipartFile     报修信息（包含图片和字段信息）
     * @param repairInfoJson    前台报修信息数据映射Json
     * @return                  回应信息封装
     */
    @Transactional
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    //使用在进行图片或者文件上传时 multipart/form-data 类型时，数据会自动进行映射不要添加任何@RequestBody注解
    public ResultMsg submitRepairInfo(@RequestParam("file") MultipartFile multipartFile, RepairInfoJson repairInfoJson) {
        try {
            RepairCurEntity repairCurEntity = repairInfoJson.submitConvert(new RepairCurEntity());

            /*保存手机号*/
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setTel(repairCurEntity.getTel());
            userInfoRepository.save(userInfoEntity);
            //将二进制图片保存在实体中
            repairCurEntity.setRepairImage(multipartFile.getBytes());
            //向数据库保存实体信息
            repairCurRepository.save(repairCurEntity);

            /*将图片输出到本地文件夹中*/
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(
                    "D:" + File.separator + "Development" + File.separator + "Workspace" + File.separator
                            + "repair" + File.separator + "repair-image" + File.separator, "123.jpg")));
            stream.write(multipartFile.getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw exceptionManager.createException("2001"); //若在保存图片过程中出现错误，则抛出一个自定义异常
        }

        return new ResultMsg().success("提交报修信息成功");
    }

    /**
     * 获取未完成报修信息接口
     * @param number        学号
     * @return              回应信息封装
     */
    @RequestMapping(value = "/userUnfinished", method = RequestMethod.GET)
    public ResultMsg getUserUnfinished(@ParamCheck String number) {
        return new ResultMsg().success(repairCurRepository.findByNumber(number));
    }


    /**
     * 获取所有报修信息接口
     * @param number        学号
     * @return              回应信息封装
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResultMsg getAllRepairInfo(@ParamCheck String number) {
        RepairInfo repairInfo = new RepairInfo(repairCurRepository.findByNumber(number), repairLogRepository.findByNumber(number));
        return new ResultMsg().success(repairInfo);
    }

    /**
     * 取消报修信息接口
     * @param id                    报修信息id
     * @param repairOrderNumber     报修信息单号
     * @param number                学号
     * @param name                  姓名
     * @return                      回应信息封装
     * @throws Exception            实体类反射异常
     */
    @Transactional
    @RequestMapping(value = "/cancelRepair", method = RequestMethod.GET)
    public ResultMsg cancelRepairCur(@ParamCheck Integer id, @ParamCheck String repairOrderNumber,
                                     @ParamCheck String number, @ParamCheck String name) throws Exception {
        Date date = new Date();
        RepairCurEntity repairCurEntity = repairCurRepository.findByIdAndRepairOrderNumber(id, repairOrderNumber);
        if (repairCurEntity != null && Objects.equals(repairCurEntity.getRepairStatus(), "待处理")) {
            RepairLogEntity repairLogEntity = new RepairLogEntity();
            ClassReflection.reflectionAttr(repairCurEntity, repairLogEntity);
            repairLogEntity.setRepairStatus("已取消");
            repairLogEntity.setUpdateTime(date);
            repairLogRepository.save(repairLogEntity);
            repairCurRepository.delete(repairCurEntity);

            return new ResultMsg().success(new RepairInfo(repairCurRepository.findByNumber(number), repairLogRepository.findByNumber(number)));

        } else {
            return new ResultMsg().failure("当前报修不能取消，请刷新后重试，或联系信息平台");
        }
    }
}
