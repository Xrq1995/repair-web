package cn.edu.ahu.repairbackend.repository;

import cn.edu.ahu.repairbackend.entity.RepairLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Author: WangMiao
 * Date: 2018/3/20
 * Time: 10:49
 * Created with IntelliJ IDEA
 * Description: 历史报修信息操作类
 */
public interface RepairLogRepository extends JpaRepository<RepairLogEntity, Integer> {

    List<RepairLogEntity> findByNumber(String number);

    @Query("select repair from RepairLogEntity repair where not repair.repairStatus = :repairStatus")
    List<RepairLogEntity> findByNotRepairStatus(@Param("repairStatus") String repairStatus);

    List<RepairLogEntity> findAll();

    RepairLogEntity findByIdAndRepairOrderNumber(Integer id, String repairOrderNumber);

    List<RepairLogEntity> findByRepairStatus(String repairStatus);

    List<RepairLogEntity> findByRepairNumberAndRepairStatus(String number, String repairStatus);
}
