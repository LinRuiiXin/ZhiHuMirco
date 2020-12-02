package com.example.recommend.controller;

import com.example.basic.po.Information;
import com.example.basic.vo.RecommendViewBean;
import com.example.recommend.service.interfaces.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/12/1 11:33 下午
 */

@RestController
@RequestMapping("/API")
public class RecommendRpcController {

    private final RecommendService recommendService;

    @Autowired
    public RecommendRpcController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @PostMapping("/UserInformation")
    public List<RecommendViewBean> getUserInformation(@RequestBody List<Information> information) throws InterruptedException, ExecutionException, IllegalAccessException, NoSuchFieldException {
        return recommendService.getInformationViewBean(information);
    }
}
