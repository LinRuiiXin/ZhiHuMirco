package com.example.answer.controller;

import com.example.answer.service.interfaces.AnswerService;
import com.example.answer.service.rpc.UserService;
import com.example.basic.dto.SimpleDto;
import com.example.basic.po.Answer;
import com.example.basic.vo.AnswerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/26 8:53 下午
 */

@RestController
@RequestMapping("/Answer")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    UserService userService;

    @Autowired
    public AnswerController(AnswerService answerService){
        this.answerService = answerService;
    }

    @PostMapping
    public SimpleDto addAnswer(MultipartHttpServletRequest request, @RequestParam Long questionId, @RequestParam Long userId, @RequestParam Integer contentType, @RequestParam String content, @RequestParam String thumbnail){
        MultipartFile answerFile = request.getFile("answer");
        if(answerFile != null){
            SimpleDto simpleDto = answerService.insertAnswer(answerFile,new Answer(questionId,userId,content,contentType,contentType == 1 ? null : thumbnail));
            return simpleDto;
        }else{
            return new SimpleDto(false,"回答不能为空,",null);
        }
    }

    @GetMapping("/{userId}/{id}")
    public SimpleDto getAnswerById(@PathVariable Long userId,@PathVariable Long id) throws ExecutionException, InterruptedException {
        AnswerVo answerById = answerService.getAnswerById(userId,id);
        return answerById.getAnswer() == null ? new SimpleDto(false,"此回答已被删除",null) : new SimpleDto(true,null,answerById);
    }

    @GetMapping("/Next/{userId}/{questionId}/{id}")
    public SimpleDto getNextAnswer(@PathVariable Long userId,@PathVariable Long questionId,@PathVariable Long id) throws ExecutionException, InterruptedException {
        AnswerVo nextAnswer = answerService.getNextAnswer(userId,questionId, id);
        return nextAnswer == null ? new SimpleDto(false,"没有更多内容了",null) : new SimpleDto(true,null,nextAnswer);
    }

    @GetMapping("/Previous/{userId}/{questionId}/{id}")
    public SimpleDto getPreviousAnswer(@PathVariable Long userId, @PathVariable Long questionId, @PathVariable Long id) throws ExecutionException, InterruptedException {
        AnswerVo nextAnswer = answerService.getPreviousAnswer(userId,questionId, id);
        return nextAnswer == null ? new SimpleDto(false,"没有更多内容了",null) : new SimpleDto(true,null,nextAnswer);
    }

    @GetMapping("/Page/{userId}/{questionId}/{id}")
    public SimpleDto getNextTreeAnswer(@PathVariable Long userId,@PathVariable Long questionId, @PathVariable Long id) throws ExecutionException, InterruptedException {
        List<AnswerVo> nextTreeAnswer = answerService.getNextTreeAnswer(userId,questionId,id);
        return new SimpleDto(true,null,nextTreeAnswer);
    }

}
