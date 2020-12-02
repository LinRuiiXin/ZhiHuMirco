package com.example.user.service.rpc;

import com.example.basic.po.Information;
import com.example.basic.vo.RecommendViewBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.concurrent.ExecutionException;

@FeignClient("Recommend")
@RequestMapping("/API")
public interface RecommendService {

    @PostMapping("/UserInformation")
    List<RecommendViewBean> getUserInformation(List<Information> information);
}
