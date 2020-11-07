package com.example.question.controller;

import com.example.question.service.interfaces.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/6 9:23 下午
 */

@RestController
@RequestMapping("/API/Question")
public class QuestionRpcController {

    private final QuestionService questionService;

    @Autowired
    public QuestionRpcController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/QuestionTitle/{questionId}")
    public String getQuestionTitle(@PathVariable Long questionId){
        return questionService.getQuestionTitle(questionId);
    }
}
