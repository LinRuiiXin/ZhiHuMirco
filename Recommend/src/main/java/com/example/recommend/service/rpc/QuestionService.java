package com.example.recommend.service.rpc;

import com.example.basic.vo.RecommendViewBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("Question")
@RequestMapping("/API")
public interface QuestionService {

    @GetMapping("/Recommend/{userId}")
    List<RecommendViewBean> recommendAnswer(@PathVariable("userId") Long userId);

}
