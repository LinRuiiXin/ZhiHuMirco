package com.example.article.dao;

import com.example.basic.po.Article;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/11 8:28 下午
 */

@Repository
public interface ArticleDao {
    void addArticle(Article article);
    Article getArticleById(Long id);
    Long getAuthorId(Long id);
    void incrementSupportSum(Long articleId);
    void decrementSupportSum(Long articleId);
    void incrementCommentSum(Long articleId);
    void decrementCommentSum(Long articleId);
}
