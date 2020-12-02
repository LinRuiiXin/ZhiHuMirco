package com.example.recommend.service;

import com.example.basic.po.Information;
import com.example.basic.po.User;
import com.example.basic.vo.RecommendViewBean;
import com.example.recommend.service.interfaces.RecommendService;
import com.example.recommend.service.rpc.*;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * TODO
 * 推荐
 * @author LinRuiXin
 * @date 2020/11/6 9:11 下午
 */
@Service
public class RecommendServiceImpl implements RecommendService {

    private final QuestionService questionService;
    private final ExecutorService executorService;
    private final UserService userService;
    private final AnswerService answerService;
    private final ArticleService articleService;

    @Autowired
    public RecommendServiceImpl(QuestionService questionService,UserService userService,ExecutorService executorService,AnswerService answerService,ArticleService articleService) {
        this.questionService = questionService;
        this.userService = userService;
        this.executorService = executorService;
        this.answerService = answerService;
        this.articleService = articleService;
    }

    /*
    * 查出用户最近8条浏览的问题类型。再根据类型随机出1-3个问题
    * */
    @Override
    public List<RecommendViewBean> getIndexRecommend(Long userId) {
        List<RecommendViewBean> recommendViewBeans = questionService.recommendAnswer(userId);
        //还应添加几条 "文章" 推荐，不过还没写，这里两条请求都需要异步。
        return recommendViewBeans;
    }

    @Override
    public List<RecommendViewBean> getInformationViewBean(List<Information> information) throws ExecutionException, InterruptedException {
        String answerIds = getInformationAnswerIds(information);
        String articleIds = getInformationArticleIds(information);
        Future<List<RecommendViewBean>> answerFuture = null;
        if(!StringUtils.isEmpty(answerIds)){
            answerFuture = executorService.submit(() -> answerService.getViewBeanBatch(answerIds));
        }
        Future<List<RecommendViewBean>> articleFuture = null;
        if(!StringUtils.isEmpty(articleIds)){
            articleFuture = executorService.submit(() -> articleService.getViewBeanBatch(articleIds));
        }
        List<RecommendViewBean> answerViewBeans = answerFuture != null ? answerFuture.get() : null;
        List<RecommendViewBean> articleViewBeans = articleFuture != null ? articleFuture.get() : null;
        if(articleViewBeans != null){
            answerViewBeans.addAll(articleViewBeans);
        }
        return answerViewBeans;
    }


    private String getInformationAnswerIds(List<Information> information) {
        List<Long> informationContentIds = getInformationContentIds(1, information);
        return joinList(informationContentIds);
    }

    private String getInformationArticleIds(List<Information> information) {
        List<Long> informationContentIds = getInformationContentIds(2, information);
        return joinList(informationContentIds);
    }

    private String joinList(List<Long> longs){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < longs.size(); i++) {
            stringBuilder.append(longs.get(i));
            if(i != longs.size()-1){
                stringBuilder.append('-');
            }
        }
        return stringBuilder.toString();
    }

    private List<Long> getInformationContentIds(int type,List<Information> information){
        List<Long> ids = new ArrayList<>();
        information.forEach(info -> {
            if(info.getType() == type){
                ids.add(info.getContentId());
            }
        });
        return ids;
    }


}











