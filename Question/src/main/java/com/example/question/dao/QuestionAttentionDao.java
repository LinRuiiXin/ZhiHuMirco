package com.example.question.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAttentionDao {
    void addNewAttention(@Param("userId")Long userId, @Param("questionId")Long questionId);
    Long getIdByQuestionIdUserId(Long questionId,Long userId);
    void removeAttention(@Param("userId")Long userId,@Param("questionId")Long questionId);
}
