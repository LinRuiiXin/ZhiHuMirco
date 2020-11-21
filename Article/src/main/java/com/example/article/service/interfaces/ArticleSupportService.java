package com.example.article.service.interfaces;

public interface ArticleSupportService {
    boolean checkUserHasSupport(Long articleId, Long userId);
    void support(Long articleId,Long userId);
    void unSupport(Long articleId,Long userId);
}
