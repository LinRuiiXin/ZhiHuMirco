package com.example.basic.po;

public class ClassifyQuestion {
    private Long id;
    private Long questionId;
    private Long classifyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    @Override
    public String toString() {
        return "ClassifyQuestion{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", classifyId=" + classifyId +
                '}';
    }
}
