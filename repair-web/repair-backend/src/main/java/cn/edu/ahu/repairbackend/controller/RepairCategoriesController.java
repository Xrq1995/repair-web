package cn.edu.ahu.repairbackend.controller;

import cn.edu.ahu.repairbackend.annotation.ParamCheck;
import cn.edu.ahu.repairbackend.entity.RepairCategoriesEntity;
import cn.edu.ahu.repairbackend.repository.RepairCategoriesRepository;
import cn.edu.ahu.repairbackend.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: WangMiao
 * Date: 2018/1/11
 * Time: 22:31
 * Created with IntelliJ IDEA
 * Description: 报修类别信息Controller
 */
@RestController
@RequestMapping(value = "/categories")
public class RepairCategoriesController {

    private final RepairCategoriesRepository repairCategoriesRepository;

    @Autowired
    public RepairCategoriesController(RepairCategoriesRepository repairCategoriesRepository) {
        this.repairCategoriesRepository = repairCategoriesRepository;
        System.out.println(123);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResultMsg getRepairCategoriesInfo() {
        List<RepairCategoriesEntity> repairCategoriesEntityList = repairCategoriesRepository.findAll();
        return new ResultMsg().success(repairCategoriesEntityList);
    }

    @RequestMapping(value = "/child", method = RequestMethod.GET)
    public ResultMsg getRepairCategoriesInfoByParent(@ParamCheck String parent) {
        RepairCategoriesEntity repairCategoriesEntityList = repairCategoriesRepository.findByParent(parent);
        return new ResultMsg().success(repairCategoriesEntityList);
    }

}
