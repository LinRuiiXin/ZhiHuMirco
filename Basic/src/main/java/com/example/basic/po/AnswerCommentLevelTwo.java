package com.example.basic.po;

import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("answerCommentLevelTwo")
public class AnswerCommentLevelTwo {
    private Long id;
    private Long levelOneId;
    private Long replyToUserId;
    private Long replyUserId;
    private String content;
    private Date date;
    private Long supportSum;

    public AnswerCommentLevelTwo() {
    }

    public AnswerCommentLevelTwo(Long levelOneId, Long replyToUserId, Long replyUserId, String content) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getSupportSum() {
        return supportSum;
    }

    public void setSupportSum(Long supportSum) {
        this.supportSum = supportSum;
    }

    @Override
    public String toString() {
        return "AnswerCommentLevelTwo{" +
                "id=" + id +
                ", levelOneId=" + levelOneId +
                ", replyToUserId=" + replyToUserId +
                ", replyUserId=" + replyUserId +
                ", Content='" + content + '\'' +
                ", date=" + date +
                ", supportSum=" + supportSum +
                '}';
    }
}
