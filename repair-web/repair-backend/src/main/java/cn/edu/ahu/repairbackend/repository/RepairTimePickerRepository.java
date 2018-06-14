package cn.edu.ahu.repairbackend.repository;

import cn.edu.ahu.repairbackend.entity.RepairTimePickerEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: WangMiao
 * Date: 2018/1/16
 * Time: 16:14
 * Created with IntelliJ IDEA
 * Description: 预约维修时间段信息操作类
 */
@Repository
@Qualifier("repairTimePicker")
public interface RepairTimePickerRepository extends JpaRepository<RepairTimePickerEntity, Integer> {

    List<RepairTimePickerEntity> findAll();

}
