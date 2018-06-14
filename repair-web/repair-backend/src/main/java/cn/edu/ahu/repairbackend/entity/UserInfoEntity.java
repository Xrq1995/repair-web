package cn.edu.ahu.repairbackend.entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.print.DocFlavor;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: WangMiao
 * Date: 2017/11/27
 * Time: 20:57
 * Created with IntelliJ IDEA
 * Description: 用户信息实体类
 */
@Entity
@Table(name = "userinfo")
public class UserInfoEntity implements Serializable {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number")
    private String number;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "college")
    private String college;

    @Column(name = "address")
    private String address;

    @Column(name = "idcard")
    private String idCard;

    @Column(name = "updatetime")
    private Date updateTime;

    @Column(name = "status")
    private String status;

    @Column(name = "password")
    private String password;

    @Column(name = "tel")
    private String tel;

    @Column(name = "role")
    private String role;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
