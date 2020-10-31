package com.example.question.service;

import com.example.basic.po.Question;
import com.example.basic.vo.RecommendQuestionViewBean;
import com.example.question.dao.QuestionDao;
import com.example.question.service.interfaces.ClassifyQuestionService;
import com.example.question.service.interfaces.QuestionAttentionService;
import com.example.question.service.interfaces.QuestionService;
import com.example.question.service.rpc.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/27 8:39 下午
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private final AsyncService asyncService;
    private final QuestionDao questionDao;
    private final UserService userService;
    private final RedisTemplate redisTemplate;
    private final QuestionAttentionService questionAttentionService;
    private final ClassifyQuestionService classifyQuestionService;

    public QuestionServiceImpl(AsyncService asyncService, QuestionDao questionDao, UserService userService, RedisTemplate redisTemplate, QuestionAttentionService questionAttentionService,ClassifyQuestionService classifyQuestionService) {
        this.asyncService = asyncService;
        this.questionDao = questionDao;
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.questionAttentionService = questionAttentionService;
        this.classifyQuestionService = classifyQuestionService;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Question addQuestion(Question question, String typeStr) {
        questionDao.insertQuestion(question);
        asyncService.setQuestionType(question.getId(),typeStr);
        return question;
    }

    @Override
    public List<RecommendQuestionViewBean> getRandomQuestion(Long userId) {
        List<Question> randomQuestion = questionDao.getRandomQuestion();
        List<RecommendQuestionViewBean> beans = new ArrayList<>();
        randomQuestion.forEach(question -> {
            RecommendQuestionViewBean recommendQuestionViewBean = new RecommendQuestionViewBean();
            recommendQuestionViewBean.setSubscribe(questionAttentionService.hasAttention(userId,question.getId()));
            recommendQuestionViewBean.setQuestion(question);
            recommendQuestionViewBean.setUser(userService.getUserById(question.getQuestionerId()));
            beans.add(recommendQuestionViewBean);
        });
        return beans;
    }
    //竞态条件
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void subscribeQuestion(Long userId, Long questionId) {
        if(!questionAttentionService.hasAttention(userId,questionId)){
            questionDao.subscribeQuestion(questionId);
            questionAttentionService.addNewAttention(userId,questionId);
        }
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void incrementAnswer(Long questionId) {
        questionDao.incrementAnswer(questionId);
    }
    //竞态条件
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void disSubscribeQuestion(Long userId, Long questionId) {
        if(questionAttentionService.hasAttention(userId,questionId)){
            questionAttentionService.removeAttention(userId,questionId);
            questionDao.disSubscribeQuestion(questionId);
        }
    }

    @Override
    public String getQuestionTitle(Long questionId) {
        return questionDao.getQuestionNameById(questionId);
    }

    @Override
    public void recordUserBrowse(Long questionId,Long userId) {
        List<Long> questionType = classifyQuestionService.getQuestionType(questionId);
        asyncService.recordUserBrowse(questionType,userId);
    }

}
