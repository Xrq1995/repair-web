package cn.edu.ahu.repairbackend.controller;

import cn.edu.ahu.repairbackend.annotation.ParamCheck;
import cn.edu.ahu.repairbackend.entity.RepairCurEntity;
import cn.edu.ahu.repairbackend.entity.UserInfoEntity;
import cn.edu.ahu.repairbackend.exception.ExceptionManager;
import cn.edu.ahu.repairbackend.repository.RepairCurRepository;
import cn.edu.ahu.repairbackend.repository.UserInfoRepository;
import cn.edu.ahu.repairbackend.util.RequestParamCheck;
import cn.edu.ahu.repairbackend.util.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Author: WangMiao
 * Date: 2017/11/27
 * Time: 22:22
 * Created with IntelliJ IDEA
 * Description: 用户信息控制类
 */
@RestController
@RequestMapping(value = "/user")
public class UserInfoController {

    private final ExceptionManager exceptionManager;
    private final UserInfoRepository userInfoRepository;
    private final RepairCurRepository repairCurRepository;

    @Autowired
    public UserInfoController(ExceptionManager exceptionManager, UserInfoRepository userInfoRepository, RepairCurRepository repairCurRepository) {
        this.exceptionManager = exceptionManager;
        this.userInfoRepository = userInfoRepository;
        this.repairCurRepository = repairCurRepository;
    }

    /**
     * 登录信息确认接口
     * @param number            学号
     * @param idCardLastSix     身份证后六位
     * @return                  用户信息回应封装
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResultMsg getUserInfoByNumberAndIdCard(@ParamCheck String number, @ParamCheck String idCardLastSix) {
        //RequestParamCheck.checkNull(number, "学号");
        //RequestParamCheck.checkNull(idCardLastSix, "身份证号后六位");

        //根据学工号查询用户信息
        UserInfoEntity userInfoEntity = userInfoRepository.findByNumber(number);

        /*未查询到对应用户信息则返回对应错误码*/
        if (userInfoEntity == null)
            throw exceptionManager.createException("1001");


        /*获得该用户身份证非字母后六位*/
        String idCard = userInfoEntity.getIdCard();
        Pattern pattern = Pattern.compile("[0-9]+[X|x]{1}");    //正则表达式，验证身份证前几位数字和最后一位是否为字母X/x
        Matcher matcher = pattern.matcher(idCard);
        String password;
        /*最后一位是字母，则取非字母的后六位*/
        if (matcher.matches()) {
            password = idCard.substring(idCard.length() - 7, idCard.length() - 1);
        } else {
            password = idCard.substring(idCard.length() - 6);
        }

        /*判断密码是否匹配*/
        if (password.equals(idCardLastSix)) {
            /*第一次登录时修改账户状态为已启用*/
            if (userInfoEntity.getStatus().equals("未启用")) {
                Date currentTime = new Date();
                userInfoEntity.setUpdateTime(currentTime);
                userInfoEntity.setStatus("已启用");
                userInfoRepository.save(userInfoEntity);
            /*账户非已启用状态时，返回对应异常码*/
            } else if (!userInfoEntity.getStatus().equals("已启用")) {
                throw exceptionManager.createException("1003");
            }
            //查询是否存在未完成的报修信息
            List<RepairCurEntity> userUnfinished = repairCurRepository.findByNumber(number);
            //用户信息组装返回
            return new ResultMsg().success(new UserInfoResponse(true, userInfoEntity, userUnfinished));
        } else {
            //账户密码不匹配返回对应异常码
            throw exceptionManager.createException("1002");
        }
    }

    /**
     * 用户信息回应封装
     */
    public static class UserInfoResponse {
        private Boolean userCheck;                      //用户信息匹配状态
        private UserInfoEntity userInfo;                //用户信息
        private List<RepairCurEntity> userUnfinished;   //未完成的报修信息

        UserInfoResponse(Boolean userCheck, UserInfoEntity userInfo, List<RepairCurEntity> userUnfinished) {
            this.userCheck = userCheck;
            this.userInfo = userInfo;
            this.userUnfinished = userUnfinished;
        }

        public Boolean getUserCheck() {
            return userCheck;
        }

        public void setUserCheck(Boolean userCheck) {
            this.userCheck = userCheck;
        }

        public UserInfoEntity getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoEntity userInfo) {
            this.userInfo = userInfo;
        }

        public List<RepairCurEntity> getUserUnfinished() {
            return userUnfinished;
        }

        public void setUserUnfinished(List<RepairCurEntity> userUnfinished) {
            this.userUnfinished = userUnfinished;
        }
    }

}
