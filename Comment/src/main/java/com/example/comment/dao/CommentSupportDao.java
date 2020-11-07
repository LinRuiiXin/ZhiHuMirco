package com.example.comment.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface CommentSupportDao {
    /*
     * 查询用户是否为该一级评论点赞，返回数据条目，如果找到一条即代表用户已点赞，因此不继续查询
     * @commentId 评论Id
     * @userId 用户Id
     * */
    Long hadSupportAnswerCommentLvOne(Long commentId, Long userId);

    /*
     * 查询用户是否为该二级评论点赞，返回数据条目，如果找到一条即代表用户已点赞，因此不继续查询
     * @replyId 回复Id
     * @userId 用户Id
     * */
    Long hadSupportAnswerCommentLvTwo(Long replyId, Long userId);

    void deleteAllAnswerCommentLvTwoSupport(Long commentLvTwoId);

    void deleteAllAnswerCommentLvOneSupport(Long commentLvOneId);

    /*
     * 点赞一级评论
     * @commentId 评论Id
     * @userId 用户Id
     * */
    void supportAnswerCommentLevelOne(Long commentId, Long userId);

    /*
     * 点赞二级评论
     * @reply 回复Id
     * @userId 用户Id
     * */
    void supportAnswerCommentLevelTwo(Long replyId, Long userId);

    /*
     * 取消点赞一级评论
     * @commentId 评论Id
     * @userId 用户Id
     * */
    void unSupportAnswerCommentLevelOne(Long commentId, Long userId);

    /*
     * 取消点赞二级评论
     * @replyId 回复Id
     * @userId 用户Id
     * */
    void unSupportAnswerCommentLevelTwo(Long replyId, Long userId);
}
