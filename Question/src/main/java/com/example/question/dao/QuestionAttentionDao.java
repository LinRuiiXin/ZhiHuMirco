package com.example.question.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAttentionDao {
    void addNewAttention(@Param("userId")Long userId, @Param("questionId")Long questionId);
    Long getIdByQuestionIdUserId(@Param("questionId") Long questionId,@Param("userId") Long userId);
    void removeAttention(@Param("userId")Long userId,@Param("questionId")Long questionId);
}
