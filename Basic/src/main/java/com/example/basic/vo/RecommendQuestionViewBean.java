package com.example.basic.vo;


import com.example.basic.po.Question;
import com.example.basic.po.User;

public class RecommendQuestionViewBean {
    private boolean isSubscribe;
    private Question question;
    private User user;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSubscribe() {
        return isSubscribe;
    }

    public void setSubscribe(boolean subscribe) {
        isSubscribe = subscribe;
    }

    @Override
    public String toString() {
        return "RecommendQuestionViewBean{" +
                "isSubscribe=" + isSubscribe +
                ", question=" + question +
                ", user=" + user +
                '}';
    }
}
