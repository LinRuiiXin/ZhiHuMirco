package com.example.basic.po;

import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/18 2:56 下午
 */

@Alias("articleCommentLevelOne")
public class ArticleCommentLevelOne {
    private Long id;
    private Long articleId;
    private Long userId;
    private String content;
    private Long supportSum;
    private int hasReply;
    private Date time;

    public ArticleCommentLevelOne() {
    }

    public ArticleCommentLevelOne(Long articleId, Long userId, String content) {
        this.articleId = articleId;
        this.userId = userId;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSupportSum() {
        return supportSum;
    }

    public void setSupportSum(Long supportSum) {
        this.supportSum = supportSum;
    }

    public int getHasReply() {
        return hasReply;
    }

    public void setHasReply(int hasReply) {
        this.hasReply = hasReply;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ArticleCommentLevelOne{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", supportSum=" + supportSum +
                ", hasReply=" + hasReply +
                ", time=" + time +
                '}';
    }
}
