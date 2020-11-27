package com.example.article.service;

import com.example.article.dao.ArticleDao;
import com.example.article.dao.InformationDao;
import com.example.article.service.interfaces.ArticleService;
import com.example.article.service.interfaces.ArticleSupportService;
import com.example.article.service.rpc.UserService;
import com.example.basic.po.Article;
import com.example.basic.po.User;
import com.example.basic.util.StringUtils;
import com.example.basic.vo.ArticleVo;
import com.example.basic.vo.RecommendViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/11 8:24 下午
 */

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleSupportService articleSupportService;

    private final InformationDao informationDao;
    private final ArticleDao articleDao;
    private final ExecutorService executorService;
    private final UserService userService;

    @Autowired
    public ArticleServiceImpl(ArticleDao articleDao, ExecutorService executorService, UserService userService, InformationDao informationDao) {
        this.articleDao = articleDao;
        this.executorService = executorService;
        this.userService = userService;
        this.informationDao = informationDao;
    }

    @Override
    public void addArticle(Article article) {
        articleDao.addArticle(article);
        informationDao.insertInformation(article.getId(),article.getAuthorId());
        userService.incrementVersion(article.getAuthorId());
    }

    /*
     * 获取文章
     * */
    @Override
    public ArticleVo getArticle(Long articleId, Long authorId, Long userId) {
        ArticleVo articleVo = null;
        try {
            articleVo = new ArticleVo();
            Future<String> content = executorService.submit(() -> {
                return getArticleContent(articleId);
            });
            if (userId != -1) {
                articleVo.setSupport(articleSupportService.checkUserHasSupport(articleId, userId));
                articleVo.setAttention(userService.whetherTheUserIsFollowed(authorId, userId));
            }
            articleVo.setContent(content.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return articleVo;
    }

    @Override
    public ArticleVo getArticle(Long articleId, Long userId) {
        Long authorId = articleDao.getAuthorId(articleId);
        return getArticle(articleId, authorId, userId);
    }

    @Override
    public void incrementSupportSum(Long articleId) {
        articleDao.incrementSupportSum(articleId);
    }

    @Override
    public void decrementSupportSum(Long articleId) {
        articleDao.decrementSupportSum(articleId);
    }

    @Override
    public void incrementCommentSum(Long articleId) {
        articleDao.incrementCommentSum(articleId);
    }

    @Override
    public void decrementCommentSum(Long articleId) {
        articleDao.decrementCommentSum(articleId);
    }

    @Override
    public RecommendViewBean getViewBean(Long id) {
        Article article = articleDao.getArticleById(id);
        User user = userService.getUserById(article.getAuthorId());
        return convertArticle(article, user);
    }


    @Override
    public List<RecommendViewBean> getViewBeanBatch(List<Long> ids) {
        List<Article> articleBatchById = articleDao.getArticleBatchById(ids);
        List<User> authorId = userService.getUserBatch(StringUtils.jointString('-', articleBatchById, "authorId"));
        List<RecommendViewBean> recommendViewBeans = convertArticle(articleBatchById, authorId);
        return recommendViewBeans;
    }



    /*
     * 根据文章Id获取具体内容
     * */
    private String getArticleContent(Long articleId) throws IOException {
        FileReader fileReader = null;
        try {
            File file = new File("/Users/linruixin/Desktop/upload/ZhiHu/Article/" + articleId + ".txt");
            if (file.exists()) {
                StringBuilder stringBuilder = new StringBuilder();
                fileReader = new FileReader(file);
                char[] buf = new char[1024];
                while (fileReader.read(buf) != -1)
                    stringBuilder.append(buf);
                return stringBuilder.toString();
            }
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
        }
        return "";
    }
    private List<RecommendViewBean> convertArticle(List<Article> articles,List<User> users){
        List<RecommendViewBean> res = new ArrayList<>(articles.size());
        for (int i = 0; i < articles.size(); i++) {
            res.add(convertArticle(articles.get(i),users.get(i)));
        }
        return res;
    }

    private RecommendViewBean convertArticle(Article article, User user) {
        return new RecommendViewBean()
                .contentId(article.getId())
                .userId(user.getId())
                .contentType(2)
                .title(article.getTitle())
                .type(article.getContentType())
                .username(user.getUserName())
                .portraitFileName(user.getPortraitFileName())
                .introduction(user.getProfile())
                .content(article.getContent())
                .thumbnail(article.getThumbnail())
                .supportSum(article.getSupportSum())
                .commentSum(article.getCommentSum())
                .date(article.getTime());
    }

}
