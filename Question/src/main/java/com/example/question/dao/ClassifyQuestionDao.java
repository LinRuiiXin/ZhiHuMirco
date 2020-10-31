package com.example.question.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassifyQuestionDao {
    void insertClassifyQuestion(@Param("questionId") Long questionId, @Param("typeId") Long typeId);
    List<Long> getQuestionType(Long questionId);
    List<Long> getRandomQuestionId(Long typeId,Integer sum);
}
