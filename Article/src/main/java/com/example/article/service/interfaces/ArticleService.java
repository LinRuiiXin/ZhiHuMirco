package com.example.article.service.interfaces;

import com.example.basic.po.Article;
import com.example.basic.vo.ArticleVo;
import com.example.basic.vo.RecommendViewBean;

import java.util.List;

public interface ArticleService {
    /*
    * 添加文章记录
    * */
    void addArticle(Article article);
    ArticleVo getArticle(Long articleId,Long authorId,Long userId);
    ArticleVo getArticle(Long articleId,Long userId);
    void incrementSupportSum(Long articleId);
    void decrementSupportSum(Long articleId);
    void incrementCommentSum(Long articleId);
    void decrementCommentSum(Long articleId);
    RecommendViewBean getViewBean(Long id);
    List<RecommendViewBean> getViewBeanBatch(List<Long> ids);
}
