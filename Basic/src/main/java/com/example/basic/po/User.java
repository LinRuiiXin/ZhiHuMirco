package com.example.basic.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/26 8:13 下午
 */

@Alias("user")
public class User implements Serializable {
    private Long id;
    private String userName;
    private String password;
    private String mail;
    private String phone;
    private Integer followSum;
    private Long fensSum;
    private Integer collectSum;
    private String profile;
    private String portraitFileName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerDate;

    public User(){}

    public User(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getFollowSum() {
        return followSum;
    }

    public void setFollowSum(Integer followSum) {
        this.followSum = followSum;
    }

    public Long getFensSum() {
        return fensSum;
    }

    public void setFensSum(Long fensSum) {
        this.fensSum = fensSum;
    }

    public Integer getCollectSum() {
        return collectSum;
    }

    public void setCollectSum(Integer collectSum) {
        this.collectSum = collectSum;
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

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", phone='" + phone + '\'' +
                ", followSum=" + followSum +
                ", fensSum=" + fensSum +
                ", collectSum=" + collectSum +
                ", profile='" + profile + '\'' +
                ", portraitFileName='" + portraitFileName + '\'' +
                ", registerDate=" + registerDate +
                '}';
    }
}
