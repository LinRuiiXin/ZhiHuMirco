package com.example.search;

import com.example.search.dao.ArticleDao;
import com.example.search.dao.QuestionDao;
import com.example.search.document.Keyword;
import com.example.search.param.ElasticSearchParam;
import com.example.search.service.interfaces.KeywordService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/27 7:47 下午
 */
@SpringBootTest
public class InitZhiHuSearch {

    private final QuestionDao questionDao;
    private final KeywordService keywordService;
    private final ArticleDao articleDao;

    @Autowired
    public InitZhiHuSearch(QuestionDao questionDao, KeywordService keywordService,ArticleDao articleDao) {
        this.questionDao = questionDao;
        this.keywordService = keywordService;
        this.articleDao = articleDao;
    }

    @Test
    void importAllQuestionKeyword(){
        List<String> allQuestion = questionDao.getAllQuestion();
        keywordService.addKeywordBatch(allQuestion);
    }

    @Test
    void importAllArticleKeyword(){
        keywordService.addKeywordBatch(articleDao.getAllArticleTitle());
    }

    @Test
    void testImportKeywordSuccess(){
        List<Keyword> allKeyword = keywordService.getAllKeyword(20);
        allKeyword.forEach(System.out::println);
    }
}
