package com.example.search.document;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/26 8:08 下午
 */
public class User {
    private Long id; //用户Id
    private String name; //用户名
    private String portraitFileName; //用户头像
    private String profile; //简介

    public User(){}

    public User(Long id, String name, String portraitFileName, String profile) {
        this.id = id;
        this.name = name;
        this.portraitFileName = portraitFileName;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortraitFileName() {
        return portraitFileName;
    }

    public void setPortraitFileName(String portraitFileName) {
        this.portraitFileName = portraitFileName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
