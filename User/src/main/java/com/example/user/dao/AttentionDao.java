package com.example.user.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttentionDao {
    Long userHasAttention(@Param("targetUserId")Long targetUserId,@Param("userId")Long userId);
    void follow(@Param("targetUserId")Long targetUserId,@Param("userId")Long userId );
    void unFollow(@Param("targetUserId")Long targetUserId,@Param("userId")Long userId );
}
