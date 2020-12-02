package com.example.user.dao;

import com.example.basic.po.User;
import com.example.basic.vo.UserAttention;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    Long getUserIdByMail(String mail);
    void insertUser(User user);
    Long getUserIdByUserName(String userName);
    User queryUserByMail(String mail);
    User queryUserByMailPassword(String mail,String password);
    Long getUserIdByPhone(String phone);
    void setPortraitFileName(@Param("id") Long id, @Param("fileName") String fileName);
    User queryUserById(Long id);
    void updateUserPassword(@Param("id") Long id,@Param("password") String password);
    String queryPasswordById(Long id);
    Integer whetherTheUserIsFollowed(Long beAttentionUserId,Long userId);
    List<UserAttention> getUserAttentions(Long userId);
    List<Integer> getUserVersionBatch(@Param("ids") List<Long> ids);
    void incrementVersion(Long id);
    void updateUser(User user);
    void incrementUserFollowSum(Long id);
    void decrementUserFollowSum(Long id);
    void incrementUserFensSum(Long id);
    void decrementUserFensSum(Long id);
}
