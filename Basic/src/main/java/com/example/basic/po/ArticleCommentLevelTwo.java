package com.example.basic.po;

import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/18 3:01 下午
 */

@Alias("articleCommentLevelTwo")
public class ArticleCommentLevelTwo {
    private Long id;
    private Long levelOneId;
    private Long replyToUserId;
    private Long replyUserId;
    private String content;
    private Long supportSum;
    private Date time;

    public ArticleCommentLevelTwo() {
    }

    public ArticleCommentLevelTwo(Long levelOneId, Long replyToUserId, Long replyUserId, String content) {
        this.levelOneId = levelOneId;
        this.replyToUserId = replyToUserId;
        this.replyUserId = replyUserId;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLevelOneId() {
        return levelOneId;
    }

    public void setLevelOneId(Long levelOneId) {
        this.levelOneId = levelOneId;
    }

    public Long getReplyToUserId() {
        return replyToUserId;
    }

    public void setReplyToUserId(Long replyToUserId) {
        this.replyToUserId = replyToUserId;
    }

    public Long getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Long replyUserId) {
        this.replyUserId = replyUserId;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ArticleCommentLevelTwo{" +
                "id=" + id +
                ", levelOneId=" + levelOneId +
                ", replyToUserId=" + replyToUserId +
                ", replyUserId=" + replyUserId +
                ", content='" + content + '\'' +
                ", supportSum=" + supportSum +
                ", time=" + time +
                '}';
    }
}
