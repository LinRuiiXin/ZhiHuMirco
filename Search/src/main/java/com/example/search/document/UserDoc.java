package com.example.search.document;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/26 8:08 下午
 */
public class UserDoc {
    private Long id; //用户Id
    private String userName; //用户名
    private String portraitFileName; //用户头像
    private String profile; //简介

    public UserDoc(){}

    public UserDoc(Long id, String userName, String portraitFileName, String profile) {
        this.id = id;
        this.userName = userName;
        this.portraitFileName = portraitFileName;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Override
    public String toString() {
        return "UserDoc{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", portraitFileName='" + portraitFileName + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }
}
