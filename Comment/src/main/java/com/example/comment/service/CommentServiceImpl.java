package com.example.comment.service;

import com.example.basic.po.AnswerCommentLevelOne;
import com.example.basic.po.AnswerCommentLevelTwo;
import com.example.basic.vo.AnswerCommentLevelOneVo;
import com.example.basic.vo.AnswerCommentLevelTwoVo;
import com.example.comment.config.RabbitConfig;
import com.example.comment.dao.CommentDao;
import com.example.comment.dao.CommentSupportDao;
import com.example.comment.service.interfaces.CommentService;
import com.example.comment.service.rpc.AnswerService;
import com.example.comment.service.rpc.UserService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * TODO
 * 评论业务层
 *
 * @author LinRuiXin
 * @date 2020/10/26 10:43 下午
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final AmqpTemplate amqpTemplate;
    private CommentDao commentDao;
    private UserService userService;
    private CommentSupportDao commentSupportDao;
    private final StringRedisTemplate redisTemplate;
    private final AnswerService answerService;
    private final ExecutorService executorService;


    @Autowired
    public CommentServiceImpl(CommentDao commentDao, UserService userService, CommentSupportDao commentSupportDao, StringRedisTemplate redisTemplate, AnswerService answerService, ExecutorService executorService, AmqpTemplate amqpTemplate) {
        this.commentDao = commentDao;
        this.userService = userService;
        this.commentSupportDao = commentSupportDao;
        this.redisTemplate = redisTemplate;
        this.answerService = answerService;
        this.executorService = executorService;
        this.amqpTemplate = amqpTemplate;
    }

    /*
     * 获取回答下一级评论
     * @answerId:回答Id
     * @userId:用户Id，判断用户是否为该评论点赞
     * @limit:分段查询，从limit往后读取10条评论
     * @return:返回一级评论包装集合
     * */
    @Override
    public List<AnswerCommentLevelOneVo> getAnswerCommentLevelOne(Long answerId, Long userId, int limit) {
        List<AnswerCommentLevelOne> answerCommentLevelOne = commentDao.getAnswerCommentLevelOne(answerId, limit);
        List<AnswerCommentLevelOneVo> res = new ArrayList<>(answerCommentLevelOne.size());
        if (answerCommentLevelOne.size() != 0) {
            for (AnswerCommentLevelOne commentLevelOne : answerCommentLevelOne) {
                AnswerCommentLevelOneVo vo = new AnswerCommentLevelOneVo();
                vo.setCommentLevelOne(commentLevelOne);
                vo.setUser(userService.getUserById(commentLevelOne.getUserId()));
                if (userId != -1)
                    vo.setSupport(commentLevelOne.getSupportSum() == 0 ? false : userHadSupportCommentLevelOne(commentLevelOne.getId(), userId));
                res.add(vo);
            }
        }
        return res;
    }

    /*
     * 获取二级评论
     * @commentLv1Id:一级评论Id
     * @userId:用户Id
     * @limit:分段查询，从limit往后10条记录
     * */
    @Override
    public List<AnswerCommentLevelTwoVo> getAnswerCommentLevelTwo(Long commentLevelOneId, Long userId, int limit) {
        List<AnswerCommentLevelTwo> answerCommentsLevelTwo = commentDao.getAnswerCommentLevelTwo(commentLevelOneId, limit);
        List<AnswerCommentLevelTwoVo> res = new ArrayList<>(answerCommentsLevelTwo.size());
        if (answerCommentsLevelTwo.size() != 0) {
            for (AnswerCommentLevelTwo answerCommentLevelTwo : answerCommentsLevelTwo) {
                AnswerCommentLevelTwoVo vo = new AnswerCommentLevelTwoVo();
                vo.setAnswerCommentLevelTwo(answerCommentLevelTwo);
                vo.setUserReplyTo(userService.getUserById(answerCommentLevelTwo.getReplyToUserId()));
                vo.setReplyUser(userService.getUserById(answerCommentLevelTwo.getReplyUserId()));
                if (userId != -1)
                    vo.setSupport(answerCommentLevelTwo.getSupportSum() == 0 ? false : userHadSupportCommentLevelTwo(answerCommentLevelTwo.getId(), userId));
                res.add(vo);
            }
        }
        return res;
    }

    /*
     * 添加一级评论
     * @answerId:回答Id
     * @userId:用户Id
     * @content:评论内容
     * */
    @Override
    public void addAnswerCommentLevelOne(Long answerId, Long userId, String content) {
        commentDao.addAnswerCommentLevelOne(answerId, userId, content);
        answerService.updateAnswerCommentSum(answerId);
    }

    /*
     * 添加二级评论
     * @commentLevelOneId : 一级评论Id
     * @userId : 用户Id
     * @replyUserId : (回复给)用户Id
     * @content : 评论内容
     * @return : 1-成功 2-一级评论已删除
     * */
    @Override
    public int addAnswerCommentLevelTwo(Long commentLevelOneId, Long userId, Long replyUserId, String content) {
        while (!tryLockCommentLevelOne(commentLevelOneId)) ;
        try {
            if (isCommentLevelOneExist(commentLevelOneId)) {
                commentDao.addAnswerCommentLevelTwo(commentLevelOneId, userId, replyUserId, content);
                commentDao.updateAnswerCommentLv1ReplySum(commentLevelOneId);
                return 1;
            } else {
                return 2;
            }
        } finally {
            unLockCommentLevelOne(commentLevelOneId);
        }
    }

    /*
     * 删除一级评论 此方法会删除一级评论及一级评论点赞及所有二级评论与点赞(异步)
     * @commentLevelOneId 一级评论Id
     * */
    @Override
    public void deleteAnswerCommentLevelOne(Long commentLevelOneId) {
        commentDao.deleteAnswerCommentLevelOne(commentLevelOneId);
        amqpTemplate.convertAndSend(RabbitConfig.COMMENT_EXCHANGE,RabbitConfig.COMMENT_LEVEL_ONE_QUEUE,commentLevelOneId);
    }

    /*
     * 删除二级评论 此方法会删除二级评论及所有点赞(异步)
     * @commentLevelOneId 二级评论Id
     * */
    @Override
    public void deleteAnswerCommentLevelTwo(Long commentLevelTwoId) {
        commentDao.deleteAnswerCommentLevelTwo(commentLevelTwoId);
        amqpTemplate.convertAndSend(RabbitConfig.COMMENT_EXCHANGE,RabbitConfig.COMMENT_LEVEL_TWO_QUEUE,commentLevelTwoId);
    }

    /*
     * 注意，此处不是线程安全，但 "点赞" 功能不需要很高的数据一致性，故不加锁(其他的点赞功能也一样)
     * 点赞一级评论
     * @commentLv1Id 一级评论Id
     * @userId 用户Id
     * */
    @Override
    public void supportAnswerCommentLevelOne(Long commentLv1Id, Long userId) {
        if (!userHadSupportCommentLevelOne(commentLv1Id, userId)) {
            commentSupportDao.supportAnswerCommentLevelOne(commentLv1Id, userId);
            commentDao.incrementAnswerCommentLv1Support(commentLv1Id);
        }
    }

    /*
     * 取消一级评论点赞，点赞数-1
     * @commentId 一级评论Id
     * @userId 点赞用户Id
     * */
    @Override
    public void unSupportAnswerCommentLevelOne(Long commentId, Long userId) {
        if (userHadSupportCommentLevelOne(commentId, userId)) {
            commentSupportDao.unSupportAnswerCommentLevelOne(commentId, userId);
            commentDao.decrementAnswerCommentLv1Support(commentId);
        }
    }

    /*
     * 点赞二级评论
     * @commentLv2Id 二级评论Id
     * @userId 用户Id
     * */
    @Override
    public void supportAnswerCommentLevelTwo(Long replyId, Long userId) {
        if (!userHadSupportCommentLevelTwo(replyId, userId)) {
            commentSupportDao.supportAnswerCommentLevelTwo(replyId, userId);
            commentDao.incrementAnswerCommentLv2Support(replyId);
        }
    }

    /*
     * 取消点赞二级评论，点赞数-1
     * @replyId 回复Id
     * @userId 点赞用户Id
     * */
    @Override
    public void unSupportAnswerCommentLevelTwo(Long replyId, Long userId) {
        if (userHadSupportCommentLevelTwo(replyId, userId)) {
            commentSupportDao.unSupportAnswerCommentLevelTwo(replyId, userId);
            commentDao.decrementAnswerCommentLv2Support(replyId);
        }
    }


    /*
     * 尝试获取二级评论锁，当用户为二级评论点赞，或删除二级评论时加锁
     * */
    private boolean tryLockCommentLevelTwo(Long commentLevelTwoId) {
        return redisTemplate.opsForValue().setIfAbsent("AnswerCommentLevelTwo::" + commentLevelTwoId, "");
    }

    private void unLockCommentLevelTwo(Long commentLevelTwoId) {
        redisTemplate.delete("AnswerCommentLevelTwo::" + commentLevelTwoId);
    }


    /*
     * 判断一级评论是否存在
     * */
    private boolean isCommentLevelOneExist(Long commentLevelOneId) {
        return commentDao.answerCommentLevelOneExist(commentLevelOneId) != null;
    }

    /*
     * 通过redis加锁，粒度为一级评论id，当增加二级评论或对一级评论删除时尝试获取锁。
     * */
    private boolean tryLockCommentLevelOne(Long commentLevelOneId) {
        return redisTemplate.opsForValue().setIfAbsent("AnswerCommentLevelOne::" + commentLevelOneId, "");
    }

    private void unLockCommentLevelOne(Long commentLevelOneId) {
        redisTemplate.delete("AnswerCommentLevelOne::" + commentLevelOneId);
    }

    //用户是否为二级评论点赞
    private boolean userHadSupportCommentLevelTwo(Long commentId, Long userId) {
        return commentSupportDao.hadSupportAnswerCommentLvTwo(commentId, userId) != null;
    }

    /*
     * 获取用户是否为一级评论点赞
     * */
    private boolean userHadSupportCommentLevelOne(Long commentId, Long userId) {
        return commentSupportDao.hadSupportAnswerCommentLvOne(commentId, userId) != null;
    }
}
