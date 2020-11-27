package com.example.search.document;

/**
 * TODO
 * 关键词如文章标题 问题标题等等
 * @author LinRuiXin
 * @date 2020/11/27 7:40 下午
 */
public class Keyword {
    private String title; //标题

    public Keyword() {
    }

    public Keyword(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "title='" + title + '\'' +
                '}';
    }
}
