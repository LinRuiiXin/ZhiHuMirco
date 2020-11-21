package com.example.article.service;

import com.example.article.dao.ArticleSupportDao;
import com.example.article.service.interfaces.ArticleService;
import com.example.article.service.interfaces.ArticleSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/12 9:41 下午
 */

@Service
public class ArticleSupportServiceImpl implements ArticleSupportService {

    @Autowired
    private ArticleService articleService;

    private final ArticleSupportDao articleSupportDao;

    @Autowired
    public ArticleSupportServiceImpl(ArticleSupportDao articleSupportDao) {
        this.articleSupportDao = articleSupportDao;
    }

    @Override
    public boolean checkUserHasSupport(Long articleId, Long userId) {
        return articleSupportDao.userHasSupportArticle(articleId,userId) != null;
    }

    @Override
    public void support(Long articleId, Long userId) {
        if(!checkUserHasSupport(articleId,userId)){
            articleSupportDao.insertSupportRecord(articleId,userId);
            articleService.incrementSupportSum(articleId);
        }
    }

    @Override
    public void unSupport(Long articleId, Long userId) {
        if(checkUserHasSupport(articleId,userId)){
            articleSupportDao.deleteSupportRecord(articleId,userId);
            articleService.decrementSupportSum(articleId);
        }
    }
}
