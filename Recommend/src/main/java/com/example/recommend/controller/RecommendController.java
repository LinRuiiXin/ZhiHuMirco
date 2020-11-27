package com.example.recommend.controller;

import com.example.basic.dto.SimpleDto;
import com.example.basic.po.Information;
import com.example.basic.vo.RecommendViewBean;
import com.example.recommend.service.interfaces.RecommendService;
import com.example.recommend.service.rpc.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

    @PostMapping("/InformationViewBean")
    public SimpleDto getInformationViewBean(@RequestBody List<Information> information) throws InterruptedException, ExecutionException, IllegalAccessException, NoSuchFieldException {
        return new SimpleDto(true,null,recommendService.getInformationViewBean(information));
    }
}
