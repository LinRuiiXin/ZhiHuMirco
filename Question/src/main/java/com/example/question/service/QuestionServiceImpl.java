package com.example.question.service;

import com.example.basic.po.Answer;
import com.example.basic.po.Question;
import com.example.basic.po.User;
import com.example.basic.vo.RecommendQuestionViewBean;
import com.example.basic.vo.RecommendViewBean;
import com.example.question.dao.QuestionDao;
import com.example.question.service.interfaces.ClassifyQuestionService;
import com.example.question.service.interfaces.QuestionAttentionService;
import com.example.question.service.interfaces.QuestionService;
import com.example.question.service.interfaces.TypeRecordService;
import com.example.question.service.rpc.AnswerService;
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
    private final TypeRecordService typeRecordService;
    private final AnswerService answerService;

    public QuestionServiceImpl(AsyncService asyncService, QuestionDao questionDao, UserService userService, RedisTemplate redisTemplate, QuestionAttentionService questionAttentionService, ClassifyQuestionService classifyQuestionService, TypeRecordService typeRecordService, AnswerService answerService) {
        this.asyncService = asyncService;
        this.questionDao = questionDao;
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.questionAttentionService = questionAttentionService;
        this.classifyQuestionService = classifyQuestionService;
        this.typeRecordService = typeRecordService;
        this.answerService = answerService;
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

    @Override
    public List<RecommendViewBean> recommendQuestion(Long userId) {
        int otherRecommend = 2;
        List<RecommendViewBean> recommendViewBeans = new ArrayList<>();
        if (userId != -1) {
            List<Long> userRecordType = typeRecordService.getUserRecordType(userId);
            List<Long> questionId = new ArrayList<>();
            userRecordType.forEach(id -> {
                questionId.addAll(classifyQuestionService.getRandomQuestion(id, 1));
            });
            questionId.stream().distinct().forEach(id -> {
                RecommendViewBean randomAnswer = getRandomAnswerViewBeanByQuestion(id);
                if (randomAnswer != null) {
                    recommendViewBeans.add(randomAnswer);
                }
            });
            if (recommendViewBeans.size() < 5) {
                otherRecommend += 5 - recommendViewBeans.size();
            }
        } else {
            otherRecommend = 5;
        }
        //获取指定个数的Answer并封装成RecommendViewBean
        recommendViewBeans.addAll(getRandomAnswerViewBean(otherRecommend));
        return recommendViewBeans;
    }

    public RecommendViewBean getRandomAnswerViewBeanByQuestion(Long id) {
        Answer answer = answerService.queryRandomAnswerByQuestionId(id);
        if (answer != null) {
            RecommendViewBean viewBean = wrapAnswer(answer);
            return viewBean;
        }
        return null;
    }

    public List<RecommendViewBean> getRandomAnswerViewBean(int sum) {
        List<RecommendViewBean> recommendViewBeans = new ArrayList<>();
        List<Answer> randomAnswer = answerService.getRandomAnswer(sum);
        randomAnswer.forEach(answer -> {
            recommendViewBeans.add(wrapAnswer(answer));
        });
        return recommendViewBeans;
    }


    private RecommendViewBean wrapAnswer(Answer answer) {
        RecommendViewBean viewBean = new RecommendViewBean();
        viewBean.setQuestionId(answer.getQuestionId());
        viewBean.setContentType(1);
        viewBean.setContent(answer.getContent());
        viewBean.setThumbnail(answer.getThumbnail());
        viewBean.setTitle(getQuestionTitle(answer.getQuestionId()));
        viewBean.setSupportSum(answer.getSupportSum());
        viewBean.setContentId(answer.getId());
        viewBean.setType(answer.getContentType());
        viewBean.setCommentSum(answer.getCommentSum());
        User user = userService.getUserById(answer.getUserId());
        viewBean.setUserId(user.getId());
        viewBean.setUsername(user.getUserName());
        viewBean.setPortraitFileName(user.getPortraitFileName());
        viewBean.setIntroduction(user.getProfile());
        return viewBean;
    }
}
