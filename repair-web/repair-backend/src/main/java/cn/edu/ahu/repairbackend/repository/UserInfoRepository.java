package cn.edu.ahu.repairbackend.repository;

import cn.edu.ahu.repairbackend.entity.UserInfoEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: WangMiao
 * Date: 2017/11/27
 * Time: 22:10
 * Created with IntelliJ IDEA
 * Description: 用户信息操作类
 */
@Repository
@Qualifier("userInfoRepository")
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {

    UserInfoEntity findByNumber(String number);

    UserInfoEntity findByNumberAndName(String number, String name);

    List<UserInfoEntity> findAllByCollege(String college);
}
