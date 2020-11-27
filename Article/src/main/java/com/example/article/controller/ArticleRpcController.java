package com.example.article.controller;

import com.example.article.service.interfaces.ArticleService;
import com.example.basic.vo.RecommendViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/18 9:42 上午
 */
@RestController
@RequestMapping("/API")
public class ArticleRpcController {

    private final ArticleService articleService;

    @Autowired
    public ArticleRpcController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PutMapping("/CommentSum/{articleId}")
    public void incrementCommentSum(@PathVariable Long articleId){
        articleService.incrementCommentSum(articleId);
    }

    @DeleteMapping("/CommentSum/{articleId}")
    public void decrementCommentSum(@PathVariable Long articleId){
        articleService.decrementCommentSum(articleId);
    }

    @GetMapping("/ArticleViewBeanBatch/{ids}")
    public List<RecommendViewBean> getViewBeanBatch(@PathVariable String ids){
        String[] split = ids.split("-");
        List<Long> idList = new ArrayList<>(split.length);
        for (String s : split) {
            idList.add(Long.valueOf(s));
        }
        return articleService.getViewBeanBatch(idList);
    }
}