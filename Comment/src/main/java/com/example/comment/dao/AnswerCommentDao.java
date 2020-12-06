package com.example.comment.dao;

import com.example.basic.po.AnswerCommentLevelOne;
import com.example.basic.po.AnswerCommentLevelTwo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerCommentDao {
    List<AnswerCommentLevelOne> getAnswerCommentLevelOne(Long answerId, int start);
    List<AnswerCommentLevelTwo> getAnswerCommentLevelTwo(@Param("levelOneId") Long levelOneId, @Param("start") int start);
    void addAnswerCommentLevelOne(AnswerCommentLevelOne levelOne);
    Long answerCommentLevelOneExist(Long commentLevelOneId);
    void addAnswerCommentLevelTwo(AnswerCommentLevelTwo levelTwo);
    void deleteAnswerCommentLevelOne(Long commentLevelOneId);
    void deleteAnswerCommentLevelTwo(Long commentLevelTwoId);
    void updateAnswerCommentLv1ReplySum(Long commentLevelOneId);
    void incrementAnswerCommentLv1Support(Long commentId);
    void incrementAnswerCommentLv2Support(Long replyId);
    void decrementAnswerCommentLv1Support(Long commentId);
    void decrementAnswerCommentLv2Support(Long replyId);
    List<Long> getAnswerCommentLevelTwoIdByLv1Id(Long commentLv1Id);
}
