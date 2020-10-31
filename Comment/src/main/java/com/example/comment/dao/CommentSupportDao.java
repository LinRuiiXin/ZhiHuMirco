package com.example.comment.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface CommentSupportDao {
    Long hadSupportAnswerCommentLvOne(Long commentId, Long userId);
    Long hadSupportAnswerCommentLvTwo(Long commentId, Long userId);
    void deleteAllAnswerCommentLvTwoSupport(Long commentLvTwoId);
    void deleteAllAnswerCommentLvOneSupport(Long commentLvOneId);
}
