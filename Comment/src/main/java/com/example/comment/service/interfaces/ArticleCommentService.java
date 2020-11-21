package com.example.comment.service.interfaces;

import com.example.basic.vo.ArticleCommentLevelOneVo;
import com.example.basic.vo.ArticleCommentLevelTwoVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ArticleCommentService {
    void addLevelOne(Long articleId,Long userId,String content);
    void addLevelTwo(Long levelOneId,Long replyToUserId,Long replyUserId,String content);
    List<ArticleCommentLevelOneVo> getLevelOne(Long articleId,Long userId,int start) throws ExecutionException, InterruptedException;
    void supportLevelOne(Long commentId,Long userId);
    void unSupportLevelOne(Long commentId,Long userId);
    void supportLevelTwo(Long replyId,Long userId);
    void unSupportLevelTwo(Long replyId,Long userId);
    List<ArticleCommentLevelTwoVo> getLevelTwo(Long levelOneId, Long userId, int start) throws ExecutionException, InterruptedException;
    void deleteLevelOne(Long commentId);
    void deleteLevelTwo(Long replyId);
}
