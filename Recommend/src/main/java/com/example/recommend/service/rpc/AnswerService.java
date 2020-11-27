package com.example.recommend.service.rpc;

import com.example.basic.vo.RecommendViewBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("Answer")
@RequestMapping("/API")
public interface AnswerService {

    @GetMapping("/AnswerViewBeanBatch/{ids}")
    List<RecommendViewBean> getViewBeanBatch(@PathVariable("ids") String ids);
}
