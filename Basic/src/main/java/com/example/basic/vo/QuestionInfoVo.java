package com.example.basic.vo;

import com.example.basic.po.Question;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/21 9:33 下午
 */
public class QuestionInfoVo {
    private boolean isAttention; //用户是否订阅该问题
    private String describe; //问题描述
    private Question question; //问题

    public QuestionInfoVo() {
    }

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "QuestionInfoVo{" +
                "isAttention=" + isAttention +
                ", describe='" + describe + '\'' +
                ", question=" + question +
                '}';
    }
}
