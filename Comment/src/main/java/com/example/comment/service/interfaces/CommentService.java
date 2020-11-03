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


    /*
     * 获取回答下一级评论
     * @answerId:回答Id
     * @userId:用户Id，判断用户是否为该评论点赞
     * @limit:分段查询，从limit往后读取10条评论
     * @return:返回一级评论包装集合
     * */
    List<AnswerCommentLevelOneVo> getAnswerCommentLevelOne(Long answerId, Long userId, int limit);

    /*
     * 获取二级评论
     * @commentLv1Id:一级评论Id
     * @userId:用户Id
     * @limit:分段查询，从limit往后10条记录
     * */
    List<AnswerCommentLevelTwoVo> getAnswerCommentLevelTwo(Long commentLevelOneId, Long userId, int limit);

    /*
     * 添加一级评论
     * @answerId:回答Id
     * @userId:用户Id
     * @content:评论内容
     * */
    void addAnswerCommentLevelOne(Long answerId,Long userId,String content);

    /*
     * 添加二级评论
     * @commentLevelOneId:一级评论Id
     * @userId:用户Id
     * @replyUserId:(回复给)用户Id
     * @content:评论内容
     * */
    int addAnswerCommentLevelTwo(Long commentLevelOneId,Long userId,Long replyUserId,String content);

    /*
    * 删除一级评论 此方法会删除一级评论及一级评论点赞及所有二级评论与点赞(异步)
    * @commentLevelOneId 一级评论Id
    * */
    void deleteAnswerCommentLevelOne(Long commentLevelOneId);

    /*
     * 删除二级评论 此方法会删除二级评论及所有点赞(异步)
     * @commentLevelOneId 二级评论Id
     * */
    void deleteAnswerCommentLevelTwo(Long commentLevelTwoId);

    /*
    * 点赞一级评论
    * @commentLv1Id 一级评论Id
    * @userId 用户Id
    * */
    void supportAnswerCommentLevelOne(Long commentLv1Id,Long userId);

    /*
    * 点赞二级评论
    * @commentLv2Id 二级评论Id
    * @userId 用户Id
    * */
    void supportAnswerCommentLevelTwo(Long commentLv2Id,Long userId);
}
