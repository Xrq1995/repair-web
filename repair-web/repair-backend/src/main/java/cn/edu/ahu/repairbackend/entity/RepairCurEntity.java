package cn.edu.ahu.repairbackend.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: WangMiao
 * Date: 2018/1/7
 * Time: 13:57
 * Created with IntelliJ IDEA
 * Description: 报修信息当前表实体
 */
@Entity
@Table(name = "repaircur")
public class RepairCurEntity implements Serializable {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number")
    private String number;

    @Column(name = "repairnumber")
    private String repairNumber;

    @Column(name = "repairordernumber")
    private String repairOrderNumber;

    @Column(name = "distributenumber")
    private String distributeNumber;

    @Column(name = "repairaddress")
    private String repairAddress;

    @Column(name = "repaircontext")
    private String repairContext;

    @Column(name = "repairtype")
    private String repairType;

    @Column(name = "repairimage")
    private byte[] repairImage;

    @Column(name = "repairstatus")
    private String repairStatus;

    @Column(name = "tel")
    private String tel;

    @Column(name = "repairdate")
    private String repairDate;

    @Column(name = "repairtime")
    private String repairTime;

    @Column(name = "createtime")
    private Date createTime;

    @Column(name = "updatetime")
    private Date updateTime;

    @Column(name = "confirmtime")
    private Date confirmTime;

    @Column(name = "distributetime")
    private Date distributeTime;

    @Column(name = "finishtime")
    private Date finishTime;

    @Column(name = "submittime")
    private Date submitTime;

    @Column(name = "messengername")
    private String messengerName;

    @Column(name = "messengernumber")
    private String messengerNumber;

    @Column(name = "repairname")
    private String repairName;

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

    public String getDistributeNumber() {
        return distributeNumber;
    }

    public void setDistributeNumber(String distributeNumber) {
        this.distributeNumber = distributeNumber;
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

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public byte[] getRepairImage() {
        return repairImage;
    }

    public void setRepairImage(byte[] repairImage) {
        this.repairImage = repairImage;
    }

    public String getRepairStatus() {
        return repairStatus;
    }

    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
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

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getMessengerName() {
        return messengerName;
    }

    public void setMessengerName(String messengerName) {
        this.messengerName = messengerName;
    }

    public String getMessengerNumber() {
        return messengerNumber;
    }

    public void setMessengerNumber(String messengerNumber) {
        this.messengerNumber = messengerNumber;
    }

    public String getRepairName() {
        return repairName;
    }

    public void setRepairName(String repairName) {
        this.repairName = repairName;
    }
}
