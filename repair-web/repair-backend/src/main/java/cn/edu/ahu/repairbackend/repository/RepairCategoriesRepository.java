package cn.edu.ahu.repairbackend.repository;

import cn.edu.ahu.repairbackend.entity.RepairCategoriesEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: WangMiao
 * Date: 2018/1/11
 * Time: 22:28
 * Created with IntelliJ IDEA
 * Description: 报修类别信息操作类
 */
@Repository
@Qualifier("repairCategoriesRepository")
public interface RepairCategoriesRepository extends JpaRepository<RepairCategoriesEntity, Integer> {

    List<RepairCategoriesEntity> findAll();

    RepairCategoriesEntity findByParent(String parent);

}
