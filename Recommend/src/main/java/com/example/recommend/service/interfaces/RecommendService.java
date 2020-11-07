package com.example.recommend.service.interfaces;

import com.example.basic.vo.RecommendViewBean;

import java.util.List;

public interface RecommendService {
    List<RecommendViewBean> getIndexRecommend(Long userId);
}
