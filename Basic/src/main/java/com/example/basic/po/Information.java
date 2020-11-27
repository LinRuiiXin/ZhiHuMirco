package com.example.basic.po;

import org.apache.ibatis.type.Alias;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/24 7:49 下午
 */

@Alias("information")
public class Information {

    private Long id;
    private Integer type;
    private Long contentId;
    private Long authorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Information{" +
                "id=" + id +
                ", type=" + type +
                ", contentId=" + contentId +
                ", authorId=" + authorId +
                '}';
    }
}
