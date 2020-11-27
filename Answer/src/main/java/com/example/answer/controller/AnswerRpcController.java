package com.example.answer.controller;

import com.example.answer.service.interfaces.AnswerService;
import com.example.basic.po.Answer;
import com.example.basic.vo.RecommendViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/RandomAnswerByQuestionId/{questionId}")
    public Answer queryRandomAnswerByQuestionId(@PathVariable Long questionId){
        return answerService.queryRandomAnswerByQuestionId(questionId);
    }

    @GetMapping("/RandomAnswer/{sum}")
    public List<Answer> getRandomAnswer(@PathVariable int sum){
        return answerService.getRandomAnswer(sum);
    }

    @GetMapping("/AnswerViewBeanBatch/{ids}")
    public List<RecommendViewBean> getViewBeanBatch(@PathVariable("ids") String ids){
        String[] split = ids.split("-");
        List<Long> idList = new ArrayList<>(split.length);
        for (String s : split) {
            idList.add(Long.valueOf(s));
        }
        return answerService.getViewBeanBatch(idList);
    }
}
