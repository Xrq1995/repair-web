package cn.edu.ahu.repairbackend.repository;

import cn.edu.ahu.repairbackend.entity.CampusInfoEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: WangMiao
 * Date: 2017/11/14
 * Time: 19:52
 * Created with IntelliJ IDEA
 * Description: 校区信息操作类
 */
@Repository
@Qualifier("campusInfoRepository")
public interface CampusInfoRepository extends JpaRepository<CampusInfoEntity, Integer> {

    List<CampusInfoEntity> findAll();

}
