package com.example.recommend.service.interfaces;

import com.example.basic.po.Information;
import com.example.basic.vo.RecommendViewBean;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface RecommendService {
    List<RecommendViewBean> getIndexRecommend(Long userId);
    List<RecommendViewBean> getInformationViewBean(List<Information> information) throws NoSuchFieldException, IllegalAccessException, ExecutionException, InterruptedException;
}
