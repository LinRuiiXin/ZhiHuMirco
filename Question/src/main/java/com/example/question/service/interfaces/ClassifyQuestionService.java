package com.example.question.service.interfaces;

import java.util.List;

public interface ClassifyQuestionService {
    void setQuestionType(Long questionId,Long classifyId);
    List<Long> getQuestionType(Long questionId);
    List<Long> getRandomQuestion(Long typeId,Integer sum);
}
