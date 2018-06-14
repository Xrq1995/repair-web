package cn.edu.ahu.repairbackend.controller;

import cn.edu.ahu.repairbackend.repository.CampusInfoRepository;
import cn.edu.ahu.repairbackend.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: WangMiao
 * Date: 2017/11/14
 * Time: 19:59
 * Created with IntelliJ IDEA
 * Description: 校区信息控制类
 */
@RestController
@RequestMapping(value = "/campus")
public class CampusInfoController {
    private final CampusInfoRepository campusInfoRepository;

    @Autowired
    public CampusInfoController(CampusInfoRepository campusInfoRepository) {

        this.campusInfoRepository = campusInfoRepository;
    }

    /**
     * 获得所有校区信息接口
     * @return  校区信息
     */
    @RequestMapping(value = "/all")
    public ResultMsg getAllCampusInfo() {
        return new ResultMsg().success(campusInfoRepository.findAll());
    }

}
