package com.example.answer.controller;

import com.example.answer.service.interfaces.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/28 2:11 下午
 */

@RestController
@RequestMapping("/API")
public class AnswerRpcController {

    private final AnswerService answerService;

    @Autowired
    public AnswerRpcController(AnswerService answerService){
        this.answerService = answerService;
    }

    @GetMapping("/Order/{questionId}")
    public List<Long> getAnswerOrderByQuestionId(@PathVariable Long questionId){
        return answerService.getAnswerOrder(questionId);
    }


    @PutMapping("/CommentSum/{answerId}")
    public void updateAnswerCommentSum(@PathVariable("answerId") Long answerId){
        answerService.updateAnswerCommentSum(answerId);
    }
}
