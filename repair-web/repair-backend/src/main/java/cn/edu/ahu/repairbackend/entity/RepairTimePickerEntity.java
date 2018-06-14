package cn.edu.ahu.repairbackend.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: WangMiao
 * Date: 2018/1/16
 * Time: 16:08
 * Created with IntelliJ IDEA
 * Description: 预约维修时间段信息实体类
 */
@Entity
@Table(name = "repairtimepicker")
public class RepairTimePickerEntity implements Serializable {

    @Id
    @Column(name= "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "starttime")
    private String startTime;

    @Column(name = "endtime")
    private String endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
