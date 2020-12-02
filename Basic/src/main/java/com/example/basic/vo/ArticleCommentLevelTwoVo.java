package com.example.basic.vo;

import com.example.basic.po.ArticleCommentLevelTwo;
import com.example.basic.po.User;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/18 2:53 下午
 */
public class ArticleCommentLevelTwoVo {
    private boolean isSupport;
    private User userReplyTo;
    private User replyUser;
    private ArticleCommentLevelTwo articleCommentLevelTwo;

    public boolean isSupport() {
        return isSupport;
    }

    public void setSupport(boolean support) {
        isSupport = support;
    }

    public User getUserReplyTo() {
        return userReplyTo;
    }

    public void setUserReplyTo(User userReplyTo) {
        this.userReplyTo = userReplyTo;
    }

    public User getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(User replyUser) {
        this.replyUser = replyUser;
    }

    public ArticleCommentLevelTwo getArticleCommentLevelTwo() {
        return articleCommentLevelTwo;
    }

    public void setArticleCommentLevelTwo(ArticleCommentLevelTwo articleCommentLevelTwo) {
        this.articleCommentLevelTwo = articleCommentLevelTwo;
    }

    @Override
    public String toString() {
        return "ArticleCommentLevelTwoVo{" +
                "isSupport=" + isSupport +
                ", userReplyTo=" + userReplyTo +
                ", replyUser=" + replyUser +
                ", articleCommentLevelTwo=" + articleCommentLevelTwo +
                '}';
    }
}
