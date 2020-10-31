package com.example.question.dao;

import com.example.basic.po.Question;
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
}
