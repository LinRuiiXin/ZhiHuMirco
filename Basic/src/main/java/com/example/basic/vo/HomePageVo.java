package com.example.basic.vo;

import com.example.basic.po.User;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/12/1 8:46 下午
 */
public class HomePageVo {
    private boolean isAttention;
    private User user;

    public HomePageVo() {
    }

    public HomePageVo(boolean isAttention, User user) {
        this.isAttention = isAttention;
        this.user = user;
    }

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "HomePageVo{" +
                "isAttention=" + isAttention +
                ", user=" + user +
                '}';
    }
}
