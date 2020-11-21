package com.example.comment.dao;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentSupportDao {
    Integer userHasSupportCommentLevelOne(@Param("commentId") Long commentId,@Param("userId") Long userId);
    void supportLevelOne(@Param("commentId")Long commentId,@Param("userId") Long userId);
    void unSupportLevelOne(@Param("commentId")Long commentId,@Param("userId") Long userId);
    Integer userHasSupportCommentLevelTwo(@Param("replyId") Long replyId,@Param("userId") Long userId);
    void supportLevelTwo(@Param("replyId")Long replyId,@Param("userId")Long userId);
    void unSupportLevelTwo(@Param("replyId")Long replyId,@Param("userId")Long userId);
    void deleteAllLevelOneSupport(Long commentId);
    void deleteAllLevelTwoSupport(Long replyId);
}
