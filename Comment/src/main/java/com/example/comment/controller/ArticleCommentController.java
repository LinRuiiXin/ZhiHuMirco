package com.example.comment.controller;

import com.example.basic.dto.SimpleDto;
import com.example.basic.po.ArticleCommentLevelOne;
import com.example.basic.po.ArticleCommentLevelTwo;
import com.example.basic.vo.ArticleCommentLevelOneVo;
import com.example.basic.vo.ArticleCommentLevelTwoVo;
import com.example.comment.service.interfaces.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * TODO
 * "文章" 评论点赞、增加以及删除，大致API与 "回答" 的相似 因此不写注释了
 * @author LinRuiXin
 * @date 2020/11/18 9:00 上午
 */
@RestController
@RequestMapping("/ArticleComment")
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @Autowired
    public ArticleCommentController(ArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
    }

    @PostMapping("/LevelOne")
    public SimpleDto addLevelOne(@RequestParam Long articleId,@RequestParam Long userId,@RequestParam String content){
        ArticleCommentLevelOne levelOne = new ArticleCommentLevelOne(articleId, userId, content);
        articleCommentService.addLevelOne(levelOne);
        return new SimpleDto(true,null,levelOne.getId());
    }

    @PostMapping("/LevelTwo")
    public SimpleDto addLevelTwo(@RequestParam Long levelOneId,@RequestParam Long replyToUserId,@RequestParam Long replyUserId,@RequestParam String content){
        ArticleCommentLevelTwo levelTwo = new ArticleCommentLevelTwo(levelOneId, replyToUserId, replyUserId, content);
        articleCommentService.addLevelTwo(levelTwo);
        return new SimpleDto(true,null,levelTwo.getId());
    }

    @GetMapping("/LevelOne/{articleId}/{userId}/{start}")
    public SimpleDto getLevelOne(@PathVariable Long articleId,@PathVariable Long userId,@PathVariable int start) throws ExecutionException, InterruptedException {
        List<ArticleCommentLevelOneVo> levelOne = articleCommentService.getLevelOne(articleId, userId, start);
        return new SimpleDto(true,null,levelOne);
    }

    @PostMapping("/LevelOne/Support")
    public SimpleDto supportLevelOne(@RequestParam Long commentId,@RequestParam Long userId){
        articleCommentService.supportLevelOne(commentId,userId);
        return SimpleDto.SUCCESS();
    }

    @PostMapping("/LevelOne/UnSupport")
    public SimpleDto unSupportLevelOne(@RequestParam Long commentId,@RequestParam Long userId){
        articleCommentService.unSupportLevelOne(commentId,userId);
        return SimpleDto.SUCCESS();
    }

    @GetMapping("/LevelTwo/{levelOneId}/{userId}/{start}")
    public SimpleDto getLevelTwo(@PathVariable Long levelOneId,@PathVariable Long userId,@PathVariable int start) throws ExecutionException, InterruptedException {
        List<ArticleCommentLevelTwoVo> vos = articleCommentService.getLevelTwo(levelOneId,userId,start);
        return new SimpleDto(true,null,vos);
    }

    @PostMapping("/LevelTwo/Support")
    public SimpleDto supportLevelTwo(@RequestParam Long replyId,@RequestParam Long userId){
        articleCommentService.supportLevelTwo(replyId,userId);
        return SimpleDto.SUCCESS();
    }

    @PostMapping("/LevelTwo/UnSupport")
    public SimpleDto unSupportLevelTwo(@RequestParam Long replyId,@RequestParam Long userId){
        articleCommentService.unSupportLevelTwo(replyId,userId);
        return SimpleDto.SUCCESS();
    }

    @DeleteMapping("/LevelOne")
    public SimpleDto deleteLevelOne(@RequestParam Long commentId){
        articleCommentService.deleteLevelOne(commentId);
        return SimpleDto.SUCCESS();
    }

    @DeleteMapping("/LevelTwo")
    public SimpleDto deleteLevelTwo(@RequestParam Long replyId){
        articleCommentService.deleteLevelTwo(replyId);
        return SimpleDto.SUCCESS();
    }
}
