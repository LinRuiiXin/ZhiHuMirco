package com.example.question.service;

import com.example.question.dao.QuestionAttentionDao;
import com.example.question.service.interfaces.QuestionAttentionService;
import com.example.question.service.interfaces.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/28 2:33 下午
 */

@Service
public class QuestionAttentionServiceImpl implements QuestionAttentionService {

    @Autowired
    private QuestionService questionService;

    private final QuestionAttentionDao questionAttentionDao;

    @Autowired
    public QuestionAttentionServiceImpl(QuestionAttentionDao questionAttentionDao){
        this.questionAttentionDao = questionAttentionDao;
    }

    @Override
    public Boolean hasAttention(Long userId, Long questionId) {
        return questionAttentionDao.getIdByQuestionIdUserId(questionId,userId)!=null;
    }

    @Override
    public void addNewAttention(Long userId, Long questionId) {
        if(!hasAttention(userId,questionId)){
            questionAttentionDao.addNewAttention(userId,questionId);
            questionService.incrementSubscribeSum(questionId);
        }

    }

    @Override
    public void removeAttention(Long userId, Long questionId) {
        if(hasAttention(userId,questionId)){
            questionAttentionDao.removeAttention(userId,questionId);
            questionService.decrementSubscribeSum(questionId);
        }
    }
}
