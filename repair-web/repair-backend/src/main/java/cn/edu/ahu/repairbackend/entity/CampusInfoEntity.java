package cn.edu.ahu.repairbackend.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: WangMiao
 * Date: 2017/11/14
 * Time: 19:49
 * Created with IntelliJ IDEA
 * Description: 校区信息实体类
 */
@Entity
@Table(name = "campusinfo")
public class CampusInfoEntity implements Serializable {

    @Id
    @Column(name= "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;					                    //主键id
    @Column(name="campuscode")
    private String code;                                    //编码
    @Column(name = "campusname")
    private String  name;                                   //名称
    @Column(name = "campus_area")
    private String area;                                    //面积
    @Column(name = "campus_allpeople")
    private String people;                                  //人数
    @Column(name = "campusdesc")
    private String description;                             //描述
    @Column(name = "state")
    private String state;                                    //状态


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
