package com.example.search.dao;

import com.example.basic.po.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/27 9:00 下午
 */
@Repository
public interface ArticleDao {
    List<String> getAllArticleTitle();
    List<Article> getAllArticle();
}
