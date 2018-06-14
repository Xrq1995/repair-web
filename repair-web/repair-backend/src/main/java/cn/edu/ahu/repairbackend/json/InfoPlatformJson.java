package cn.edu.ahu.repairbackend.json;

import cn.edu.ahu.repairbackend.entity.RepairCurEntity;
import cn.edu.ahu.repairbackend.util.CreateOrderNumber;
import com.sun.org.apache.regexp.internal.RE;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: WangMiao
 * Date: 2018/1/17
 * Time: 14:08
 * Created with IntelliJ IDEA
 * Description: 信息平台信息Json解析类
 */
public class InfoPlatformJson implements Serializable {

    private Integer id;
    private String number;
    private String repairDate;
    private String repairTime;
    private String repairAddress;
    private String repairContext;
    private Date confirmTime;
    private Date createTime;
    private Date distributeTime;
    private Date finishTime;
    private Date updateTime;
    private Date submitTime;
    private String distributeNumber;
    private String repairNumber;
    private String repairOrderNumber;
    private String repairStatus;
    private String repairType;
    private String tel;
    private String messengerNumber;
    private String messengerName;

    public RepairCurEntity editConvert(RepairCurEntity repairCurEntity) {
        repairCurEntity.setNumber(number);
        repairCurEntity.setTel(tel);
        repairCurEntity.setRepairDate(repairDate);
        repairCurEntity.setRepairTime(repairTime);
        repairCurEntity.setRepairAddress(repairAddress);
        repairCurEntity.setRepairType(repairType);
        repairCurEntity.setRepairContext(repairContext);
        repairCurEntity.setMessengerNumber(messengerNumber);
        repairCurEntity.setMessengerName(messengerName);

        Date date = new Date();
        repairCurEntity.setUpdateTime(date);

        return repairCurEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(String repairDate) {
        this.repairDate = repairDate;
    }

    public String getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
    }

    public String getRepairAddress() {
        return repairAddress;
    }

    public void setRepairAddress(String repairAddress) {
        this.repairAddress = repairAddress;
    }

    public String getRepairContext() {
        return repairContext;
    }

    public void setRepairContext(String repairContext) {
        this.repairContext = repairContext;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(Date distributeTime) {
        this.distributeTime = distributeTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getDistributeNumber() {
        return distributeNumber;
    }

    public void setDistributeNumber(String distributeNumber) {
        this.distributeNumber = distributeNumber;
    }

    public String getRepairNumber() {
        return repairNumber;
    }

    public void setRepairNumber(String repairNumber) {
        this.repairNumber = repairNumber;
    }

    public String getRepairOrderNumber() {
        return repairOrderNumber;
    }

    public void setRepairOrderNumber(String repairOrderNumber) {
        this.repairOrderNumber = repairOrderNumber;
    }

    public String getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMessengerNumber() {
        return messengerNumber;
    }

    public void setMessengerNumber(String messengerNumber) {
        this.messengerNumber = messengerNumber;
    }

    public String getMessengerName() {
        return messengerName;
    }

    public void setMessengerName(String messengerName) {
        this.messengerName = messengerName;
    }
}
