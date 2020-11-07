package com.example.recommend.service.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("Article")
@RequestMapping("/API")
public interface ArticleService {
}
