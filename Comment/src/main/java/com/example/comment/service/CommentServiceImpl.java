package com.example.comment.service;

import com.example.basic.po.AnswerCommentLevelOne;
import com.example.basic.po.AnswerCommentLevelTwo;
import com.example.basic.vo.AnswerCommentLevelOneVo;
import com.example.basic.vo.AnswerCommentLevelTwoVo;
import com.example.comment.dao.CommentDao;
import com.example.comment.dao.CommentSupportDao;
import com.example.comment.service.interfaces.CommentService;
import com.example.comment.service.rpc.AnswerService;
import com.example.comment.service.rpc.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/26 10:43 下午
 */
public class CommentServiceImpl implements CommentService {

    private CommentDao commentDao;
    private UserService userService;
    private CommentSupportDao commentSupportDao;
    private final StringRedisTemplate redisTemplate;
    private final AnswerService answerService;
    private final ExecutorService executorService;



    @Autowired
    public CommentServiceImpl(CommentDao commentDao, UserService userService, CommentSupportDao commentSupportDao, StringRedisTemplate redisTemplate, AnswerService answerService, ExecutorService executorService) {
        this.commentDao = commentDao;
        this.userService = userService;
        this.commentSupportDao = commentSupportDao;
        this.redisTemplate = redisTemplate;
        this.answerService = answerService;
        this.executorService = executorService;
    }


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
                if(userId != -1)
                    vo.setSupport(answerCommentLevelTwo.getSupportSum() == 0 ? false : userHadSupportCommentLevelTwo(answerCommentLevelTwo.getId(),userId));
                res.add(vo);
            }
        }
        return res;
    }

    @Override
    public void addAnswerCommentLevelOne(Long answerId, Long userId, String content) {
        /*commentDao.addAnswerCommentLevelOne(answerId,userId,content);
        answerService.updateAnswerCommentSum(answerId);*/
    }

    /*
     * 添加二级评论
     * 返回状态码：1-成功 2-一级评论已删除
     * */
    @Override
    public int addAnswerCommentLevelTwo(Long commentLevelOneId, Long userId, Long replyUserId, String content) {
        while(!tryLockCommentLevelOne(commentLevelOneId));
        try{
            if(isCommentLevelOneExist(commentLevelOneId)){
                commentDao.addAnswerCommentLevelTwo(commentLevelOneId,userId,replyUserId,content);
                commentDao.updateAnswerCommentLv1ReplySum(commentLevelOneId);
                return 1;
            }else{
                return 2;
            }
        }finally {
            unLockCommentLevelOne(commentLevelOneId);
        }
    }

    @Override
    public void deleteAnswerCommentLevelOne(Long commentLevelOneId) {
        while (tryLockCommentLevelOne(commentLevelOneId));
        try{
            commentDao.deleteAnswerCommentLevelOne(commentLevelOneId);
        }finally {
            unLockCommentLevelOne(commentLevelOneId);
        }
        //还未完善
        deleteAllAnswerCommentLevelOneRecord(commentLevelOneId);
    }

    @Override
    public void deleteAnswerCommentLevelTwo(Long commentLevelTwoId) {
        /*while(!tryLockCommentLevelTwo(commentLevelTwoId));
        try{
            commentDao.deleteAnswerCommentLevelTwo(commentLevelTwoId);
        }finally {
            unLockCommentLevelTwo(commentLevelTwoId);
        }
        commentSupportDao.deleteAllAnswerCommentLvTwoSupport(commentLevelTwoId);*/
    }


    /*
     * 尝试获取二级评论锁，当用户为二级评论点赞，或删除二级评论时加锁
     * */
    private boolean tryLockCommentLevelTwo(Long commentLevelTwoId) {
        return redisTemplate.opsForValue().setIfAbsent("AnswerCommentLevelTwo::"+commentLevelTwoId,"");
    }

    private void unLockCommentLevelTwo(Long commentLevelTwoId){
        redisTemplate.delete("AnswerCommentLevelTwo::"+commentLevelTwoId);
    }


    /*
     * 删除一切与此一级评论相关的回复，点赞
     * ！！！未完善！！！
     * */
    private void deleteAllAnswerCommentLevelOneRecord(Long commentLevelOneId) {
        executorService.submit(()->{
            deleteAllAnswerCommentLvTwoAboutLvOne(commentLevelOneId);
            deleteAllAnswerCommentLvOneSupport(commentLevelOneId);
        });
    }


    /*
     * 删除所有一级评论的点赞，不加锁，因为此时一级评论已被删除。不能被点赞。
     * */
    private void deleteAllAnswerCommentLvOneSupport(Long commentLevelOneId) {
//        commentSupportDao.deleteAllAnswerCommentLvOneSupport(commentLevelOneId);
    }

    /*
     * 删除所有该一级评论下的二级评论
     * */
    private void deleteAllAnswerCommentLvTwoAboutLvOne(Long commentLevelOneId) {
        List<Long> commentLvTwoIds = commentDao.getAnswerCommentLvTwoIdFromLvOneId(commentLevelOneId);
        commentLvTwoIds.forEach(this::deleteAnswerCommentLevelTwo);
    }

    /*
     * 判断一级评论是否存在
     * */
    private boolean isCommentLevelOneExist(Long commentLevelOneId){
        return commentDao.answerCommentLevelOneExist(commentLevelOneId) != null;
    }

    /*
     * 通过redis加锁，粒度为一级评论id，当增加二级评论或对一级评论删除时尝试获取锁。
     * */
    private boolean tryLockCommentLevelOne(Long commentLevelOneId) {
        return redisTemplate.opsForValue().setIfAbsent("AnswerCommentLevelOne::" + commentLevelOneId, "");
    }

    private void unLockCommentLevelOne(Long commentLevelOneId) {
        redisTemplate.delete("AnswerCommentLevelOne::"+commentLevelOneId);
    }

    //用户是否为二级评论点赞
    private boolean userHadSupportCommentLevelTwo(Long commentId,Long userId) {
//        return commentSupportDao.hadSupportAnswerCommentLvTwo(commentId,userId) != null;
        return false;
    }

    /*
     * 获取用户是否为一级评论点赞
     * */
    private boolean userHadSupportCommentLevelOne(Long commentId, Long userId) {
//        return commentSupportDao.hadSupportAnswerCommentLvOne(commentId, userId) != null;
        return false;
    }
}
