package cn.edu.ahu.repairbackend.domain;

import cn.edu.ahu.repairbackend.entity.RepairCurEntity;
import cn.edu.ahu.repairbackend.entity.RepairLogEntity;

import java.util.List;

/**
 * Author: WangMiao
 * Date: 2018/3/20
 * Time: 10:56
 * Created with IntelliJ IDEA
 * Description: 报修信息domain类
 */
public class RepairInfo {

    private List<RepairCurEntity> repairCurEntityList;
    private List<RepairLogEntity> repairLogEntityList;

    public RepairInfo(List<RepairCurEntity> repairCurEntityList, List<RepairLogEntity> repairLogEntityList) {
        this.repairCurEntityList = repairCurEntityList;
        this.repairLogEntityList = repairLogEntityList;
    }

    public List<RepairCurEntity> getRepairCurEntityList() {
        return repairCurEntityList;
    }

    public void setRepairCurEntityList(List<RepairCurEntity> repairCurEntityList) {
        this.repairCurEntityList = repairCurEntityList;
    }

    public List<RepairLogEntity> getRepairLogEntityList() {
        return repairLogEntityList;
    }

    public void setRepairLogEntityList(List<RepairLogEntity> repairLogEntityList) {
        this.repairLogEntityList = repairLogEntityList;
    }
}
