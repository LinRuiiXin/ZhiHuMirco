package com.example.basic.vo;


import com.example.basic.po.Answer;
import com.example.basic.po.User;

public class AnswerVo {
    private boolean isAttention;
    private boolean isSupport;
    private User user;
    private Answer answer;
    public AnswerVo(){}

    public AnswerVo(boolean isAttention, boolean isSupport, User user, Answer answer) {
        this.isAttention = isAttention;
        this.isSupport = isSupport;
        this.user = user;
        this.answer = answer;
    }

    public AnswerVo(User user, Answer answer) {
        this.user = user;
        this.answer = answer;
    }

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }

    public boolean isSupport() {
        return isSupport;
    }

    public void setSupport(boolean support) {
        isSupport = support;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "AnswerVo{" +
                "isAttention=" + isAttention +
                ", isSupport=" + isSupport +
                ", user=" + user +
                ", answer=" + answer +
                '}';
    }
}
