package com.example.question.service.interfaces;

import com.example.basic.po.Question;
import com.example.basic.vo.QuestionInfoVo;
import com.example.basic.vo.RecommendQuestionViewBean;
import com.example.basic.vo.RecommendViewBean;

import java.util.List;

public interface QuestionService {
    Question addQuestion(Question question, String typeStr);
    List<RecommendQuestionViewBean> getRandomQuestion(Long userId);
    void subscribeQuestion(Long userId,Long questionId);
    void incrementAnswer(Long questionId);
    void disSubscribeQuestion(Long userId,Long questionId);
    String getQuestionTitle(Long questionId);
    void recordUserBrowse(Long questionId,Long userId);
    List<RecommendViewBean> recommendQuestion(Long userId);
    QuestionInfoVo getQuestionInfo(Long questionId, Long userId);
    void incrementSubscribeSum(Long questionId);
    void decrementSubscribeSum(Long questionId);
    List<Question> getHotList(int from,int size);
}
