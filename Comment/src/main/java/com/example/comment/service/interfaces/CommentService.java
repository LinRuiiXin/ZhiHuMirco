package com.example.comment.service.interfaces;

import com.example.basic.vo.AnswerCommentLevelOneVo;
import com.example.basic.vo.AnswerCommentLevelTwoVo;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/26 10:43 下午
 */
public interface CommentService {
    List<AnswerCommentLevelOneVo> getAnswerCommentLevelOne(Long answerId, Long userId, int limit);
    List<AnswerCommentLevelTwoVo> getAnswerCommentLevelTwo(Long commentLevelOneId, Long userId, int limit);
    void addAnswerCommentLevelOne(Long answerId,Long userId,String content);
    int addAnswerCommentLevelTwo(Long commentLevelOneId,Long userId,Long replyUserId,String content);
    void deleteAnswerCommentLevelOne(Long commentLevelOneId);
    void deleteAnswerCommentLevelTwo(Long commentLevelTwoId);
}
