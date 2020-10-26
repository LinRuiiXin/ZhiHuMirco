package com.example.basic.po;

import org.apache.ibatis.type.Alias;

@Alias("article")
public class Article {
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private String thumbnail;
    private Integer contentType;
    private Long supportSum;
    private Long commentSum;

    public Article(){}

    public Article(Long authorId, String title, String content, String thumbnail, Integer contentType) {
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.contentType = contentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
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
        return "Article{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", contentType=" + contentType +
                ", supportSum=" + supportSum +
                ", commentSum=" + commentSum +
                '}';
    }
}
