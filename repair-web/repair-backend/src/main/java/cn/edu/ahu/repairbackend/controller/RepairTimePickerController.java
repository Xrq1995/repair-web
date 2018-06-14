package cn.edu.ahu.repairbackend.controller;

import cn.edu.ahu.repairbackend.entity.RepairTimePickerEntity;
import cn.edu.ahu.repairbackend.repository.RepairTimePickerRepository;
import cn.edu.ahu.repairbackend.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: WangMiao
 * Date: 2018/1/16
 * Time: 16:23
 * Created with IntelliJ IDEA
 * Description: 预约维修时间段信息控制类
 */
@RestController
@RequestMapping(value = "/timePicker")
public class RepairTimePickerController {

    private final RepairTimePickerRepository repairTimePickerRepository;

    @Autowired
    public RepairTimePickerController(RepairTimePickerRepository repairTimePickerRepository) {
        this.repairTimePickerRepository = repairTimePickerRepository;
    }

    /**
     * 获得选择预约时间段信息接口
     * @return  预约时间段信息
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResultMsg getRepairTimePickerInfo() {
        List<RepairTimePickerEntity> repairTimePickerEntityList = repairTimePickerRepository.findAll();
        return new ResultMsg().success(repairTimePickerEntityList);
    }

}
