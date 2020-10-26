package com.example.basic.po;

import org.apache.ibatis.type.Alias;

@Alias("collect")
public class Collect {
    private Long id;
    private Long userId;
    private Long contentId;
    private Long questionId;
    private Integer contentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "Collect{" +
                "id=" + id +
                ", userId=" + userId +
                ", contentId=" + contentId +
                ", questionId=" + questionId +
                ", contentType=" + contentType +
                '}';
    }
}
