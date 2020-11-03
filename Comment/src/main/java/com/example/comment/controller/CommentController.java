package com.example.comment.controller;

import com.example.basic.dto.SimpleDto;
import com.example.basic.vo.AnswerCommentLevelOneVo;
import com.example.basic.vo.AnswerCommentLevelTwoVo;
import com.example.comment.service.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO
 * 此控制器负责路由"评论"请求
 * @author LinRuiXin
 * @date 2020/10/28 3:18 下午
 */

@RestController
@RequestMapping("/Comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    /*
    * 获取回答下一级评论
    * @answerId:回答Id
    * @userId:用户Id，判断用户是否为该评论点赞
    * @limit:分段查询，从limit往后读取10条评论
    * @return:返回一级评论包装集合
    * */

    @GetMapping("/LevelOne/{answerId}/{userId}/{limit}")
    public SimpleDto getAnswerCommentLevelOne(@PathVariable Long answerId, @PathVariable Long userId, @PathVariable int limit){
        List<AnswerCommentLevelOneVo> answerCommentLevelOne = commentService.getAnswerCommentLevelOne(answerId, userId, limit);
        return new SimpleDto(true,null,answerCommentLevelOne);
    }

    /*
    * 获取二级评论
    * @commentLv1Id:一级评论Id
    * @userId:用户Id
    * @limit:分段查询，从limit往后10条记录
    * */
    @GetMapping("/LevelTwo/{commentLv1Id}/{userId}/{limit}")
    public SimpleDto getCommentLevelTwo(@PathVariable Long commentLv1Id,@PathVariable Long userId,@PathVariable int limit){
        List<AnswerCommentLevelTwoVo> answerCommentLevelTwo = commentService.getAnswerCommentLevelTwo(commentLv1Id, userId, limit);
        return new SimpleDto(true,null,answerCommentLevelTwo);
    }

    /*
    * 添加一级评论
    * @answerId:回答Id
    * @userId:用户Id
    * @content:评论内容
    * */
    @PostMapping("/LevelOne")
    public SimpleDto addCommentLevelOne(@RequestParam Long answerId,@RequestParam Long userId,@RequestParam String content){
        commentService.addAnswerCommentLevelOne(answerId,userId,content);
        return new SimpleDto(true,null,null);
    }

    /*
     * 添加二级评论
     * @commentLevelOneId:一级评论Id
     * @userId:用户Id
     * @replyUserId:(回复给)用户Id
     * @content:评论内容
     * */
    @PostMapping("/LevelTwo")
    public SimpleDto addCommentLevelTwo(@RequestParam Long commentLevelOneId,@RequestParam Long userId,@RequestParam Long replyUserId,@RequestParam String content){
        int i = commentService.addAnswerCommentLevelTwo(commentLevelOneId, userId, replyUserId, content);
        SimpleDto simpleDto = null;
        switch (i){
            case 1:
                simpleDto = new SimpleDto(true,"回复成功",null);
                break;
            case 2:
                simpleDto = new SimpleDto(false,"抱歉，此评论已删除",null);
                break;
            default:
                simpleDto = new SimpleDto(false,"无效状态码",null);
        }
        return simpleDto;
    }
}

