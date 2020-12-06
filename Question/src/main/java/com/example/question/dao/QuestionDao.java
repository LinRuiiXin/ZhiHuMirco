package com.example.question.dao;

import com.example.basic.po.Question;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao {
    void insertQuestion(Question question);
    List<Question> getRandomQuestion();
    void subscribeQuestion(Long questionId);
    void incrementAnswer(Long questionId);
    void disSubscribeQuestion(Long questionId);
    String getQuestionNameById(Long questionId);
    Question getQuestionById(Long questionId);
    void incrementSubscribeSum(Long questionId);
    void decrementSubscribeSum(Long questionId);
    void incrementBrowseSum(Long questionId);
    List<Question> getHotList(@Param("from") int from,@Param("size") int size);
}
