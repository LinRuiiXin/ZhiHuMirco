package com.example.question.service.interfaces;

import com.example.basic.po.Question;
import com.example.basic.vo.RecommendQuestionViewBean;

import java.util.List;

public interface QuestionService {
    Question addQuestion(Question question, String typeStr);
    List<RecommendQuestionViewBean> getRandomQuestion(Long userId);
    void subscribeQuestion(Long userId,Long questionId);
    void incrementAnswer(Long questionId);
    void disSubscribeQuestion(Long userId,Long questionId);
    String getQuestionTitle(Long questionId);
    void recordUserBrowse(Long questionId,Long userId);
}
