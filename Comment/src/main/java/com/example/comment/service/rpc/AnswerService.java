package com.example.comment.service.rpc;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("Answer")
public interface AnswerService {
}
