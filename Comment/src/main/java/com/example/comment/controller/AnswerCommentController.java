package com.example.comment.controller;

import com.example.basic.dto.SimpleDto;
import com.example.basic.po.AnswerCommentLevelOne;
import com.example.basic.po.AnswerCommentLevelTwo;
import com.example.basic.vo.AnswerCommentLevelOneVo;
import com.example.basic.vo.AnswerCommentLevelTwoVo;
import com.example.comment.service.interfaces.AnswerCommentService;
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
@RequestMapping("/AnswerComment")
public class AnswerCommentController {
    private final AnswerCommentService commentService;

    @Autowired
    public AnswerCommentController(AnswerCommentService commentService){
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
        AnswerCommentLevelOne answerCommentLevelOne = new AnswerCommentLevelOne(answerId, userId, content);
        commentService.addAnswerCommentLevelOne(answerCommentLevelOne);
        return new SimpleDto(true,null,answerCommentLevelOne.getId());
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
        AnswerCommentLevelTwo levelTwo = new AnswerCommentLevelTwo(commentLevelOneId, userId, replyUserId, content);
        int i = commentService.addAnswerCommentLevelTwo(levelTwo);
        SimpleDto simpleDto = null;
        switch (i){
            case 1:
                simpleDto = new SimpleDto(true,"回复成功",levelTwo.getId());
                break;
            case 2:
                simpleDto = new SimpleDto(false,"抱歉，此评论已删除",null);
                break;
            default:
                simpleDto = new SimpleDto(false,"无效状态码",null);
        }
        return simpleDto;
    }

    /*
    * 点赞一级评论，点赞数+1
    * @commentId 一级评论Id
    * @userId 点赞用户Id
    * */
    @PostMapping("/LevelOne/Support")
    public SimpleDto supportCommentLevelOne(@RequestParam Long commentId,@RequestParam Long userId){
        commentService.supportAnswerCommentLevelOne(commentId,userId);
        return new SimpleDto(true,null,null);
    }

    /*
    * 取消一级评论点赞，点赞数-1
    * @commentId 一级评论Id
    * @userId 点赞用户Id
    * */
    @PostMapping("/LevelOne/UnSupport")
    public SimpleDto unSupportCommentLevelOne(@RequestParam Long commentId,@RequestParam Long userId){
        commentService.unSupportAnswerCommentLevelOne(commentId,userId);
        return new SimpleDto(true,null,null);
    }

    /*
    * 点赞二级评论，点赞数+1
    * @replyId 回复Id，即对一级评论的回复
    * @userId 点赞用户Id
    * */
    @PostMapping("/LevelTwo/Support")
    public SimpleDto supportCommentLevelTwo(@RequestParam Long replyId,@RequestParam Long userId){
        commentService.supportAnswerCommentLevelTwo(replyId,userId);
        return new SimpleDto(true,null,null);
    }

    /*
    * 取消点赞二级评论，点赞数-1
    * @replyId 回复Id
    * @userId 点赞用户Id
    * */
    @PostMapping("/LevelTwo/UnSupport")
    public SimpleDto unSupportCommentLevelTwo(@RequestParam Long replyId,@RequestParam Long userId){
        commentService.unSupportAnswerCommentLevelTwo(replyId,userId);
        return new SimpleDto(true,null,null);
    }

    /*
    * 删除一级评论
    * @commentId 一级评论Id
    * */
    @DeleteMapping("/LevelOne")
    public SimpleDto deleteAnswerCommentLevelOne(@RequestParam Long commentId){
        commentService.deleteAnswerCommentLevelOne(commentId);
        return new SimpleDto(true,null,null);
    }

    /*
    * 删除二级评论
    * @commentId 二级评论Id
    * */
    @DeleteMapping("/LevelTwo")
    public SimpleDto deleteAnswerCommentLevelTwo(@RequestParam Long replyId){
        commentService.deleteAnswerCommentLevelTwo(replyId);
        return new SimpleDto(true,null,null);
    }
}

