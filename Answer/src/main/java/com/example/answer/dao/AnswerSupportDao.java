package com.example.answer.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerSupportDao {
    void supportAnswer(@Param("answerId") Long answerId,@Param("userId") Long userId);
    void unSupportAnswer(@Param("answerId") Long answerId,@Param("userId") Long userId);
}
