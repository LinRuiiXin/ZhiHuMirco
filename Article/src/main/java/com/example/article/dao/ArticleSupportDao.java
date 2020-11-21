package com.example.article.dao;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleSupportDao {
    Integer userHasSupportArticle(@Param("articleId") Long articleId, @Param("userId") Long userId);
    void insertSupportRecord(@Param("articleId") Long articleId,@Param("userId") Long userId);
    void deleteSupportRecord(@Param("articleId") Long articleId,@Param("userId") Long userId);
}
