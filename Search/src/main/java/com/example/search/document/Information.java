package com.example.search.document;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/26 8:08 下午
 */
public class Information {

    private int contentType; // 1-回答 2-文章
    private Long questionId;//问题id，回答有，文章没有
    private int type; //1-纯文本 2-图片 3-视频
    private Long contentId; //内容Id
    private String title; //标题
    private String content; //内容(完整)
    private String thumbnail; //缩略图(纯文本则没有)
    private Long authorId; //作者Id
    private String authorName; //作者名
    private String profile;//个人简介
    private String portraitFileName; //头像文件名

    public Information() {
    }

    public Information(int contentType, Long questionId, int type, Long contentId, String title, String content, String thumbnail, Long authorId, String authorName, String profile, String portraitFileName) {
        this.contentType = contentType;
        this.questionId = questionId;
        this.type = type;
        this.contentId = contentId;
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.authorId = authorId;
        this.authorName = authorName;
        this.profile = profile;
        this.portraitFileName = portraitFileName;
    }

    public int contentType() {
        return contentType;
    }

    public Information contentType(int contentType) {
        this.contentType = contentType;
        return this;
    }

    public Long questionId() {
        return questionId;
    }

    public Information questionId(Long questionId) {
        this.questionId = questionId;
        return this;
    }

    public int type() {
        return type;
    }

    public Information type(int type) {
        this.type = type;
        return this;
    }

    public Long contentId() {
        return contentId;
    }

    public Information contentId(Long contentId) {
        this.contentId = contentId;
        return this;
    }

    public String title() {
        return title;
    }

    public Information title(String title) {
        this.title = title;
        return this;
    }

    public String content() {
        return content;
    }

    public Information content(String content) {
        this.content = content;
        return this;
    }

    public String thumbnail() {
        return thumbnail;
    }

    public Information thumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public Long authorId() {
        return authorId;
    }

    public Information authorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public String authorName() {
        return authorName;
    }

    public Information authorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public String profile() {
        return profile;
    }

    public Information profile(String profile) {
        this.profile = profile;
        return this;
    }

    public String portraitFileName() {
        return portraitFileName;
    }

    public Information portraitFileName(String portraitFileName) {
        this.portraitFileName = portraitFileName;
        return this;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPortraitFileName() {
        return portraitFileName;
    }

    public void setPortraitFileName(String portraitFileName) {
        this.portraitFileName = portraitFileName;
    }
}
