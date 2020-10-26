package com.example.comment.dao;

import com.example.basic.po.AnswerCommentLevelOne;
import com.example.basic.po.AnswerCommentLevelTwo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {
    List<AnswerCommentLevelOne> getAnswerCommentLevelOne(Long answerId, int start);
    List<AnswerCommentLevelTwo> getAnswerCommentLevelTwo(@Param("levelOneId") Long levelOneId, @Param("start") int start);
    void addAnswerCommentLevelOne(@Param("answerId") Long answerId,@Param("userId")Long userId,@Param("content")String content);
    Long answerCommentLevelOneExist(Long commentLevelOneId);
    void addAnswerCommentLevelTwo(@Param("commentLevelOneId")Long commentLevelOneId,@Param("userId")Long userId,@Param("replyUserId")Long replyId,@Param("content")String content);
    void deleteAnswerCommentLevelOne(Long commentLevelOneId);
    //    一级评论回复数+1
    void updateAnswerCommentLv1ReplySum(Long commentLevelOneId);
    void deleteAnswerCommentLevelTwo(Long commentLevelTwoId);
    List<Long> getAnswerCommentLvTwoIdFromLvOneId(Long levelOneId);
}
