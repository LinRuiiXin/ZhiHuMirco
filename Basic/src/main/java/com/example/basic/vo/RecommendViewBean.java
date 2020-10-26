package com.example.basic.vo;

public class RecommendViewBean {
    private Long contentId;//内容id，用contentType区分文章、回答
    private Long questionId;//问题id，回答有，文章没有
    private Long userId;//用户id
    private int contentType; //1-回答 2.文章
    private int type;//1-纯文本 2-带图片 3-带视频
    private String title;//标题
    private String username;//用户名
    private String portraitFileName;//用户头像
    private String introduction;//个人简介
    private String content;//内容
    private String thumbnail;
    private Long supportSum;//点赞数
    private Long commentSum;//评论数

    public RecommendViewBean() {
    }

    public RecommendViewBean(Long contentId, Long questionId, Long userId, int contentType, int type, String title, String username, String portraitFileName, String introduction, String content, Long supportSum, Long commentSum) {
        this.contentId = contentId;
        this.questionId = questionId;
        this.userId = userId;
        this.contentType = contentType;
        this.type = type;
        this.title = title;
        this.username = username;
        this.portraitFileName = portraitFileName;
        this.introduction = introduction;
        this.content = content;
        this.supportSum = supportSum;
        this.commentSum = commentSum;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
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

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPortraitFileName() {
        return portraitFileName;
    }

    public void setPortraitFileName(String portraitFileName) {
        this.portraitFileName = portraitFileName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "RecommendViewBean{" +
                "contentId=" + contentId +
                ", questionId=" + questionId +
                ", userId=" + userId +
                ", contentType=" + contentType +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", username='" + username + '\'' +
                ", portraitFileName='" + portraitFileName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", content='" + content + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", supportSum=" + supportSum +
                ", commentSum=" + commentSum +
                '}';
    }
}
