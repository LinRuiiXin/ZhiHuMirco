package com.example.comment.dao;

import com.example.basic.po.ArticleCommentLevelOne;
import com.example.basic.po.ArticleCommentLevelTwo;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCommentDao {
    void addLevelOne(@Param("articleId") Long articleId,@Param("userId") Long userId,@Param("content") String content);
    void addLevelTwo(@Param("levelOneId")Long levelOneId,@Param("replyToUserId")Long replyToUserId,@Param("replyUserId")Long replyUserId,@Param("content")String content);
    void incrementLevelOneReplySum(Long levelOneId);
    void decrementLevelOneReplySum(Long levelOneId);
    List<ArticleCommentLevelOne> getLevelOne(@Param("articleId")Long articleId,@Param("start")int start);
    void incrementLevelOneSupportSum(Long commentId);
    void decrementLevelOneSupportSum(Long commentId);
    void incrementLevelTwoSupportSum(Long replyId);
    void decrementLevelTwoSupportSum(Long replyId);
    List<ArticleCommentLevelTwo> getLevelTwo(@Param("levelOneId")Long levelOneId,@Param("start")int start);
    void deleteLevelOne(Long commentId);
    List<Long> getReplyIdsByCommentId(Long commentId);
    void deleteLevelTwo(Long replyId);
}
