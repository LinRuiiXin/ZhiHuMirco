package com.example.basic.po;

import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("answer")
public class Answer {
    private Long id;
    private Long questionId;
    private Long userId;
    private String content;
    private Integer contentType;
    private String thumbnail;
    private Long supportSum;
    private Long commentSum;
    private Date time;

    public Answer() {
    }

    public Answer(Long questionId, Long userId, String content, Integer contentType) {
        this.questionId = questionId;
        this.userId = userId;
        this.content = content;
        this.contentType = contentType;
    }

    public Answer(Long questionId, Long userId, String content, Integer contentType, String thumbnail) {
        this.questionId = questionId;
        this.userId = userId;
        this.content = content;
        this.contentType = contentType;
        this.thumbnail = thumbnail;
    }

    public Answer(Long id, Long questionId, Long userId, String content, Integer contentType, Long supportSum, Long commentSum, Date time) {
        this.id = id;
        this.questionId = questionId;
        this.userId = userId;
        this.content = content;
        this.contentType = contentType;
        this.supportSum = supportSum;
        this.commentSum = commentSum;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Long getSupportSum() {
        return supportSum;
    }

    public void setSupportSum(Long supportSum) {
        this.supportSum = supportSum;
    }

    public Long getCommentSum() {
        return commentSum;
    }

    public void setCommentSum(Long commentSum) {
        this.commentSum = commentSum;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", contentType=" + contentType +
                ", thumbnail='" + thumbnail + '\'' +
                ", supportSum=" + supportSum +
                ", commentSum=" + commentSum +
                ", time=" + time +
                '}';
    }
}

