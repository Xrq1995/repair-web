package cn.edu.ahu.repairbackend.json;

import cn.edu.ahu.repairbackend.entity.RepairCurEntity;
import cn.edu.ahu.repairbackend.entity.RepairLogEntity;
import cn.edu.ahu.repairbackend.entity.UserInfoEntity;
import cn.edu.ahu.repairbackend.repository.UserInfoRepository;
import cn.edu.ahu.repairbackend.util.CreateOrderNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: WangMiao
 * Date: 2018/1/17
 * Time: 14:08
 * Created with IntelliJ IDEA
 * Description: 前台报修信息Json解析类
 */
public class RepairInfoJson implements Serializable {

    private Integer id;
    private String name;
    private String number;
    private String tel;
    private String address;
    private String selectedParent;
    private String selectedChild;
    private String context;
    private String date;
    private String time;
    private Date submitTime;

    /*private final UserInfoRepository userInfoRepository;

    @Autowired
    public RepairInfoJson(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    private static ApplicationContext applicationContext;//启动类set入，调用下面set方法

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public void getBeanTest(){
        TestService testService  = (TestService)applicationContext.getBean(TestService.class);
    }*/

    public RepairCurEntity submitConvert(RepairCurEntity repairCurEntity) {
        /*String tel = repairCurEntity.getTel();
        if (tel != null && !tel.isEmpty()) {
            UserInfoEntity userInfoEntity = userInfoRepository.findByNumberAndName(repairCurEntity.getNumber(), repairCurEntity.getMessengerName());
            userInfoEntity.setTel(tel);
            userInfoRepository.save(userInfoEntity);
        }*/

        repairCurEntity.setNumber(number);
        repairCurEntity.setRepairAddress(address);
        repairCurEntity.setRepairContext(context);
        repairCurEntity.setRepairType(selectedParent + '-' + selectedChild);
        repairCurEntity.setTel(tel);
        repairCurEntity.setRepairDate(date);
        repairCurEntity.setRepairTime(time);
        repairCurEntity.setSubmitTime(submitTime);
        repairCurEntity.setRepairStatus("待处理");

        Date date = new Date();
        repairCurEntity.setCreateTime(date);
        repairCurEntity.setUpdateTime(date);
        repairCurEntity.setRepairOrderNumber(CreateOrderNumber.createOrderNumber(date, id));

        return repairCurEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSelectedParent() {
        return selectedParent;
    }

    public void setSelectedParent(String selectedParent) {
        this.selectedParent = selectedParent;
    }

    public String getSelectedChild() {
        return selectedChild;
    }

    public void setSelectedChild(String selectedChild) {
        this.selectedChild = selectedChild;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

}
