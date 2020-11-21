package com.example.basic.vo;

import com.example.basic.po.ArticleCommentLevelOne;
import com.example.basic.po.User;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/18 2:53 下午
 */
public class ArticleCommentLevelOneVo {
    private boolean isSupport;
    private User user;
    private ArticleCommentLevelOne articleCommentLevelOne;

    public ArticleCommentLevelOneVo() {
    }

    public ArticleCommentLevelOneVo(boolean isSupport, User user, ArticleCommentLevelOne articleCommentLevelOne) {
        this.isSupport = isSupport;
        this.user = user;
        this.articleCommentLevelOne = articleCommentLevelOne;
    }

    public boolean isSupport() {
        return isSupport;
    }

    public void setSupport(boolean support) {
        isSupport = support;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArticleCommentLevelOne getArticleCommentLevelOne() {
        return articleCommentLevelOne;
    }

    public void setArticleCommentLevelOne(ArticleCommentLevelOne articleCommentLevelOne) {
        this.articleCommentLevelOne = articleCommentLevelOne;
    }

    @Override
    public String toString() {
        return "ArticleCommentLevelOneVo{" +
                "isSupport=" + isSupport +
                ", user=" + user +
                ", articleCommentLevelOne=" + articleCommentLevelOne +
                '}';
    }
}
