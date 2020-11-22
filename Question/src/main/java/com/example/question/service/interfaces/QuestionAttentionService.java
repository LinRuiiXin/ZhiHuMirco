package com.example.question.service.interfaces;

public interface QuestionAttentionService {
    //判断用户是否订阅该问题
    Boolean hasAttention(Long userId,Long questionId);
    //用户订阅问题
    void addNewAttention(Long userId,Long questionId);
    //取消订阅
    void removeAttention(Long userId,Long questionId);
}
