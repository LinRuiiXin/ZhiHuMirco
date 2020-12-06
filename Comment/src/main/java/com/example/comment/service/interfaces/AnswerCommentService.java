package com.example.comment.service.interfaces;

import com.example.basic.po.AnswerCommentLevelOne;
import com.example.basic.po.AnswerCommentLevelTwo;
import com.example.basic.vo.AnswerCommentLevelOneVo;
import com.example.basic.vo.AnswerCommentLevelTwoVo;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/26 10:43 下午
 */
public interface AnswerCommentService {

    List<AnswerCommentLevelOneVo> getAnswerCommentLevelOne(Long answerId, Long userId, int limit);

    List<AnswerCommentLevelTwoVo> getAnswerCommentLevelTwo(Long commentLevelOneId, Long userId, int limit);

    void addAnswerCommentLevelOne(AnswerCommentLevelOne levelOne);

    int addAnswerCommentLevelTwo(AnswerCommentLevelTwo levelTwo);

    void deleteAnswerCommentLevelOne(Long commentLevelOneId);

    void deleteAnswerCommentLevelTwo(Long commentLevelTwoId);

    void supportAnswerCommentLevelOne(Long commentLv1Id, Long userId);

    void supportAnswerCommentLevelTwo(Long commentLv2Id, Long userId);

    void unSupportAnswerCommentLevelOne(Long commentId, Long userId);

    void unSupportAnswerCommentLevelTwo(Long replyId, Long userId);

}
