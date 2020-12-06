package com.example.basic.po;

import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("answerCommentLevelOne")
public class AnswerCommentLevelOne {
    private Long id;
    private Long answerId;
    private Long userId;
    private String content;
    private Date time;
    private Long supportSum;
    private int hasReply;

    public AnswerCommentLevelOne() {
    }

    public AnswerCommentLevelOne(Long answerId, Long userId, String content) {
        this.answerId = answerId;
        this.userId = userId;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    @Override
    public String toString() {
        return "CommentLevelOne{" +
                "id=" + id +
                ", answerId=" + answerId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", supportSum=" + supportSum +
                ", hasReply=" + hasReply +
                '}';
    }
}
