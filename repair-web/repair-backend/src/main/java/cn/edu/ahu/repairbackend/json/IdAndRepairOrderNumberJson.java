package cn.edu.ahu.repairbackend.json;

import java.io.Serializable;
import java.util.function.IntToDoubleFunction;

/**
 * Author: WangMiao
 * Date: 2018/5/18
 * Time: 18:17
 * Created with IntelliJ IDEA
 * Description:
 */
public class IdAndRepairOrderNumberJson implements Serializable {

    private Integer id;
    private String repairOrderNumber;
    private String messengerName;
    private String messengerNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRepairOrderNumber() {
        return repairOrderNumber;
    }

    public void setRepairOrderNumber(String repairOrderNumber) {
        this.repairOrderNumber = repairOrderNumber;
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
}

