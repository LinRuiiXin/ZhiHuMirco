package com.example.recommend.controller;

import com.example.basic.dto.SimpleDto;
import com.example.basic.vo.RecommendViewBean;
import com.example.recommend.service.interfaces.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/6 8:53 下午
 */
@RestController
@RequestMapping("/Recommend")
public class RecommendController {
    private final RecommendService recommendService;

    @Autowired
    public RecommendController(RecommendService recommendService){
        this.recommendService = recommendService;
    }

    @GetMapping("/Index/{userId}")
    public SimpleDto getIndexRecommend(@PathVariable Long userId){
        List<RecommendViewBean> indexRecommend = recommendService.getIndexRecommend(userId);
        return new SimpleDto(true,null,indexRecommend);
    }
}
