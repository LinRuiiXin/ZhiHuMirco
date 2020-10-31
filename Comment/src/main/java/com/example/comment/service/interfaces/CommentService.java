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


    void deleteAnswerCommentLevelOne(Long commentLevelOneId);
    void deleteAnswerCommentLevelTwo(Long commentLevelTwoId);
}
