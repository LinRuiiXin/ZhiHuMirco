package com.example.comment.service;

import com.example.basic.po.ArticleCommentLevelOne;
import com.example.basic.po.ArticleCommentLevelTwo;
import com.example.basic.po.User;
import com.example.basic.vo.ArticleCommentLevelOneVo;
import com.example.basic.vo.ArticleCommentLevelTwoVo;
import com.example.comment.config.RabbitConfig;
import com.example.comment.dao.ArticleCommentDao;
import com.example.comment.dao.ArticleCommentSupportDao;
import com.example.comment.service.interfaces.ArticleCommentService;
import com.example.comment.service.rpc.ArticleService;
import com.example.comment.service.rpc.UserService;
import com.sun.mail.imap.protocol.ID;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/18 9:09 上午
 */
@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    private final ArticleCommentDao articleCommentDao;
    private final ArticleCommentSupportDao articleCommentSupportDao;
    private final ArticleService articleService;
    private final ExecutorService executorService;
    private final UserService userService;
    private final AmqpTemplate amqpTemplate;

    @Autowired
    public ArticleCommentServiceImpl(ArticleCommentDao articleCommentDao, ArticleCommentSupportDao articleCommentSupportDao, ArticleService articleService, ExecutorService executorService, UserService userService, AmqpTemplate amqpTemplate) {
        this.articleCommentDao = articleCommentDao;
        this.articleCommentSupportDao = articleCommentSupportDao;
        this.articleService = articleService;
        this.executorService =  executorService;
        this.userService = userService;
        this.amqpTemplate = amqpTemplate;
    }

    @Transactional
    @Override
    public void addLevelOne(Long articleId, Long userId, String content) {
        articleCommentDao.addLevelOne(articleId,userId,content);
        articleService.incrementCommentSum(articleId);
    }

    @Transactional
    @Override
    public void addLevelTwo(Long levelOneId, Long replyToUserId, Long replyUserId,String content) {
        articleCommentDao.addLevelTwo(levelOneId,replyToUserId,replyUserId,content);
        articleCommentDao.incrementLevelOneReplySum(levelOneId);
    }

    @Override
    public List<ArticleCommentLevelOneVo> getLevelOne(Long articleId, Long userId, int start) throws ExecutionException, InterruptedException {
        List<ArticleCommentLevelOne> levelOne = articleCommentDao.getLevelOne(articleId, start);
        List<ArticleCommentLevelOneVo> vos = new ArrayList<>(levelOne.size());
        if(levelOne.size() > 0){
            Future<List<User>> submit = executorService.submit(() -> {return userService.getUserBatch(getLevelOneIds(levelOne));});
            List<Boolean> userHasSupportCommentBatch = getUserHasSupportLevelOneBatch(userId, levelOne);
            List<User> users = submit.get();
            for(int i = 0;i < levelOne.size();i++){
                ArticleCommentLevelOneVo articleCommentLevelOneVo = new ArticleCommentLevelOneVo(userHasSupportCommentBatch.get(i), users.get(i), levelOne.get(i));
                vos.add(articleCommentLevelOneVo);
            }
        }
        return vos;
    }

    @Override
    public void supportLevelOne(Long commentId, Long userId) {
        if(!userHasSupportCommentLevelOne(commentId,userId)){
            articleCommentSupportDao.supportLevelOne(commentId,userId);
            articleCommentDao.incrementLevelOneSupportSum(commentId);
        }
    }

    @Override
    public void unSupportLevelOne(Long commentId, Long userId) {
        if(userHasSupportCommentLevelOne(commentId,userId)){
            articleCommentSupportDao.unSupportLevelOne(commentId,userId);
            articleCommentDao.decrementLevelOneSupportSum(commentId);
        }
    }

    @Override
    public void supportLevelTwo(Long replyId, Long userId) {
        if(!userHasSupportCommentLevelTwo(replyId,userId)){
            articleCommentSupportDao.supportLevelTwo(replyId,userId);
            articleCommentDao.incrementLevelTwoSupportSum(replyId);
        }
    }

    @Override
    public void unSupportLevelTwo(Long replyId, Long userId) {
        if(userHasSupportCommentLevelTwo(replyId,userId)){
            articleCommentSupportDao.unSupportLevelTwo(replyId,userId);
            articleCommentDao.decrementLevelTwoSupportSum(replyId);
        }
    }

    @Override
    public List<ArticleCommentLevelTwoVo> getLevelTwo(Long levelOneId, Long userId, int start) throws ExecutionException, InterruptedException {
        List<ArticleCommentLevelTwo> levelTwos = articleCommentDao.getLevelTwo(levelOneId, start);
        List<ArticleCommentLevelTwoVo> res = new ArrayList<>(levelTwos.size());
        if(levelTwos.size() > 0){
            Future<List<User>> future = executorService.submit(()->{return userService.getUserBatch(getLevelTwoIds(levelTwos));});
            List<Boolean> userHasSupportLevelTwoBatch = getUserHasSupportLevelTwoBatch(userId, levelTwos);
            List<User> users = future.get();
            for (int i = 0; i < levelTwos.size(); i++) {
                ArticleCommentLevelTwoVo levelTwoVo = new ArticleCommentLevelTwoVo();
                levelTwoVo.setSupport(userHasSupportLevelTwoBatch.get(i));
                levelTwoVo.setUserReplyTo(users.get(i*2));
                levelTwoVo.setReplyUser(users.get(i*2+1));
                levelTwoVo.setArticleCommentLevelTwo(levelTwos.get(i));
                res.add(levelTwoVo);
            }
        }
        return res;
    }

    @Override
    public void deleteLevelOne(Long commentId) {
        articleCommentDao.deleteLevelOne(commentId);
        amqpTemplate.convertAndSend(RabbitConfig.COMMENT_EXCHANGE,RabbitConfig.ARTICLE_COMMENT_LEVEL_ONE_QUEUE,commentId);
    }

    @Override
    public void deleteLevelTwo(Long replyId) {
        articleCommentDao.deleteLevelTwo(replyId);
        amqpTemplate.convertAndSend(RabbitConfig.COMMENT_EXCHANGE,RabbitConfig.ARTICLE_COMMENT_LEVEL_TWO_QUEUE);
    }

    private List<Boolean> getUserHasSupportLevelTwoBatch(Long userId, List<ArticleCommentLevelTwo> levelTwos){
        List<Boolean> res = new ArrayList<>(levelTwos.size());
        if(userId == -1){
            for(int i = 0;i < levelTwos.size();i++)
                res.add(false);
        }else
            levelTwos.forEach(lv2 -> res.add(userHasSupportCommentLevelTwo(lv2.getId(),userId)));
        return res;
    }

    private List<Boolean> getUserHasSupportLevelOneBatch(Long userId, List<ArticleCommentLevelOne> levelOne) {
        List<Boolean> res = new ArrayList<>(levelOne.size());
        if(userId == -1){
            for(int i = 0;i < levelOne.size();i++)
                res.add(false);
        }else
            levelOne.forEach(lv1 -> res.add(userHasSupportCommentLevelOne(lv1.getId(),userId)));
        return res;
    }

    private boolean userHasSupportCommentLevelOne(Long id,Long userId ) {
        return articleCommentSupportDao.userHasSupportCommentLevelOne(id,userId) != null;
    }
    private boolean userHasSupportCommentLevelTwo(Long replyId,Long userId ) {
        return articleCommentSupportDao.userHasSupportCommentLevelTwo(replyId,userId) != null;
    }

    /*
    * 获取一级评论的所有用户Id，以 1-2-3-4-5 的形式返回
    * */
    private String getLevelOneIds(List<ArticleCommentLevelOne> levelOne) {
        StringBuilder stringBuilder = new StringBuilder();
        int len = levelOne.size();
        for (int i = 0; i < len; i++) {
            stringBuilder.append(levelOne.get(i).getUserId());
            if(i != len-1){
                stringBuilder.append("-");
            }
        }
        return stringBuilder.toString();
    }

    /*
    * 获取二级评论的 "被回复者" 及 "回复者" id
    * 格式：1-2-3-4-5
    * */
    private String getLevelTwoIds(List<ArticleCommentLevelTwo> levelTwos){
        StringBuilder stringBuilder = new StringBuilder();
        int len = levelTwos.size();
        for (int i = 0; i < len; i++) {
            ArticleCommentLevelTwo levelTwo = levelTwos.get(i);
            stringBuilder.append(levelTwo.getReplyToUserId() + "-" + levelTwo.getReplyUserId());
            if(i != len-1)
                stringBuilder.append("-");
        }
        return stringBuilder.toString();
    }

}
