package com.example.comment.service.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("Article")
@RequestMapping("/API")
public interface ArticleService {

    @PutMapping("/CommentSum/{articleId}")
    void incrementCommentSum(@PathVariable("articleId") Long articleId);

    @DeleteMapping("/CommentSum/{articleId}")
    void decrementCommentSum(@PathVariable("articleId") Long articleId);

}
