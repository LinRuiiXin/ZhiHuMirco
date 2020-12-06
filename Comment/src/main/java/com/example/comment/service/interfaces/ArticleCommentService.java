package com.example.comment.service.interfaces;

import com.example.basic.po.ArticleCommentLevelOne;
import com.example.basic.po.ArticleCommentLevelTwo;
import com.example.basic.vo.ArticleCommentLevelOneVo;
import com.example.basic.vo.ArticleCommentLevelTwoVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ArticleCommentService {
    void addLevelOne(ArticleCommentLevelOne levelOne);
    void addLevelTwo(ArticleCommentLevelTwo levelTwo);
    List<ArticleCommentLevelOneVo> getLevelOne(Long articleId,Long userId,int start) throws ExecutionException, InterruptedException;
    void supportLevelOne(Long commentId,Long userId);
    void unSupportLevelOne(Long commentId,Long userId);
    void supportLevelTwo(Long replyId,Long userId);
    void unSupportLevelTwo(Long replyId,Long userId);
    List<ArticleCommentLevelTwoVo> getLevelTwo(Long levelOneId, Long userId, int start) throws ExecutionException, InterruptedException;
    void deleteLevelOne(Long commentId);
    void deleteLevelTwo(Long replyId);
}
