package cn.edu.ahu.repairbackend.repository;

import cn.edu.ahu.repairbackend.entity.RepairCurEntity;
import cn.edu.ahu.repairbackend.entity.RepairLogEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: WangMiao
 * Date: 2018/1/7
 * Time: 18:35
 * Created with IntelliJ IDEA
 * Description: 当前报修信息操作类
 */
@Repository
@Qualifier("repairCurRepository")
public interface RepairCurRepository extends JpaRepository<RepairCurEntity, Integer> {

    List<RepairCurEntity> findByNumber(String number);

    RepairCurEntity findByIdAndRepairOrderNumber(Integer id, String repairOrderNumber);

    List<RepairCurEntity> findByRepairStatus(String repairStatus);

    List<RepairCurEntity> findAll();

    @Query("select repair from RepairCurEntity repair where not repair.repairStatus = :repairStatus")
    List<RepairCurEntity> findByNotRepairStatus(@Param("repairStatus") String repairStatus);

    List<RepairCurEntity> findByRepairNumberAndRepairStatus(String number, String repairStatus);
}
