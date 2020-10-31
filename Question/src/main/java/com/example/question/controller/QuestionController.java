package com.example.question.controller;

import com.example.basic.dto.SimpleDto;
import com.example.basic.po.Question;
import com.example.question.service.interfaces.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/27 8:18 下午
 */

@RestController
@RequestMapping("/Question")
public class QuestionController {


    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

//    获取随机推荐问题
    @GetMapping("/Random/{userId}")
    public SimpleDto getRandomQuestion(@PathVariable("userId")Long userId){
        return new SimpleDto(true,null,questionService.getRandomQuestion(userId));
    }

    //添加问题，可以携带问题描述文件，如果有，记为有问题描述，否则反之。回答的类型按 id1-id2-id3 的形式
    @PostMapping
    public SimpleDto addNewQuestion(MultipartHttpServletRequest request, Long userId, String questionName, String typeStr){
        Question question = new Question(questionName, userId);
        MultipartFile describe = request.getFile("describe");
        if (describe == null) {
            question.setHasDescribe(0);
            questionService.addQuestion(question, typeStr);
            return new SimpleDto(true,null,null);
        } else {
            try {
                question.setHasDescribe(1);
                Question newQuestion = questionService.addQuestion(question,typeStr);
                File file = new File("/Question/"+newQuestion.getId()+".txt");
                describe.transferTo(file);
                return new SimpleDto(true,null,null);
            } catch (IOException e) {
                e.printStackTrace();
                return new SimpleDto(false,"上传失败",null);
            }
        }
    }

//    记录用户浏览问题
    @RequestMapping("/Browse/{questionId}/{userId}")
    public void recordUserBrowse(@PathVariable Long questionId,@PathVariable Long userId){
        questionService.recordUserBrowse(questionId,userId);
    }

}
