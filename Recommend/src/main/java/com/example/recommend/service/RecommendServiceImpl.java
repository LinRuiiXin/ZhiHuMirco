package com.example.recommend.service;

import com.example.basic.po.Answer;
import com.example.basic.po.Article;
import com.example.basic.po.User;
import com.example.basic.vo.RecommendViewBean;
import com.example.recommend.service.interfaces.RecommendService;
import com.example.recommend.service.rpc.*;
import com.example.recommend.util.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * 推荐
 * @author LinRuiXin
 * @date 2020/11/6 9:11 下午
 */
@Service
public class RecommendServiceImpl implements RecommendService {

    private final QuestionService questionService;
    private final UserService userService;
    private final AnswerService answerService;
    private final ArticleService articleService;

    @Autowired
    public RecommendServiceImpl(QuestionService questionService, UserService userService, AnswerService answerService, ArticleService articleService) {
        this.questionService = questionService;
        this.userService = userService;
        this.answerService = answerService;
        this.articleService = articleService;
    }

    /*
    * 查出用户最近8条浏览的问题类型。再根据类型随机出1-3个问题
    * */
    @Override
    public List<RecommendViewBean> getIndexRecommend(Long userId) {
        int otherRecommend = 2;
        List<RecommendViewBean> recommendViewBeans = new ArrayList<>();
        if (userId != -1) {
            List<Long> userRecordType = questionService.getUserRecordType(userId);
            List<Long> questionId = new ArrayList<>();
            userRecordType.forEach(id -> {
                questionId.addAll(questionService.getRandomQuestion(id, 1));
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
        //获取随机一片文章并封装成RecommendViewBean
//        recommendViewBeans.addAll(getRandomArticleViewBean());
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


    private List<RecommendViewBean> getRandomArticleViewBean() {
      /*  List<RecommendViewBean> recommendViewBeans = new ArrayList<>();
        List<Article> randomArticle = articleService.getRandomArticle(RandomUtils.getRangeNumber(2));
        randomArticle.forEach(article -> {
            RecommendViewBean viewBean = new RecommendViewBean();
            viewBean.setTitle(article.getTitle());
            viewBean.setCommentSum(article.getCommentSum());
            viewBean.setSupportSum(article.getSupportSum());
            viewBean.setContentType(2);
            User user = userService.getUserById(article.getAuthorId());
            viewBean.setUserId(user.getId());
            viewBean.setUsername(user.getUserName());
            viewBean.setPortraitFileName(user.getPortraitFileName());
            viewBean.setIntroduction(user.getProfile());
            viewBean.setContentId(article.getId());
            recommendViewBeans.add(viewBean);
        });
        return recommendViewBeans;*/
        return null;
    }


    private RecommendViewBean wrapAnswer(Answer answer) {
        RecommendViewBean viewBean = new RecommendViewBean();
        viewBean.setQuestionId(answer.getQuestionId());
        viewBean.setContentType(1);
        viewBean.setContent(answer.getContent());
        viewBean.setThumbnail(answer.getThumbnail());
        viewBean.setTitle(questionService.getQuestionTitle(answer.getQuestionId()));
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











