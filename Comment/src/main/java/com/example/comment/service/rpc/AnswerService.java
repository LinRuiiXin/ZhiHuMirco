package com.example.comment.service.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("Answer")
@RequestMapping("/API")
public interface AnswerService {

    @PutMapping("/CommentSum/{answerId}")
    void updateAnswerCommentSum(@PathVariable("answerId") Long answerId);
}
