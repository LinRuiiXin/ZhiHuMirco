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

    @Autowired
    public RecommendServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
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



}











