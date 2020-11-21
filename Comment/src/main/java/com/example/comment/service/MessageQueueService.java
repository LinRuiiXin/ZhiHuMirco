package com.example.comment.service;

import com.example.comment.config.RabbitConfig;
import com.example.comment.dao.AnswerCommentDao;
import com.example.comment.dao.AnswerCommentSupportDao;
import com.example.comment.dao.ArticleCommentDao;
import com.example.comment.dao.ArticleCommentSupportDao;
import com.example.comment.service.interfaces.AnswerCommentService;
import com.example.comment.service.interfaces.ArticleCommentService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/5 2:50 下午
 */

@Service
public class MessageQueueService {

    private final AmqpTemplate amqpTemplate;
    private final AnswerCommentDao answerCommentDao;
    private final AnswerCommentSupportDao answerCommentSupportDao;
    private final AnswerCommentService answerCommentService;
    private final ArticleCommentDao articleCommentDao;
    private final ArticleCommentSupportDao articleCommentSupportDao;
    private final ArticleCommentService articleCommentService;

    @Autowired
    public MessageQueueService(AmqpTemplate amqpTemplate, AnswerCommentDao answerCommentDao, AnswerCommentSupportDao answerCommentSupportDao, AnswerCommentService answerCommentService, ArticleCommentDao articleCommentDao, ArticleCommentSupportDao articleCommentSupportDao, ArticleCommentService articleCommentService) {
        this.amqpTemplate = amqpTemplate;
        this.answerCommentDao = answerCommentDao;
        this.answerCommentSupportDao = answerCommentSupportDao;
        this.answerCommentService = answerCommentService;
        this.articleCommentDao = articleCommentDao;
        this.articleCommentSupportDao = articleCommentSupportDao;
        this.articleCommentService = articleCommentService;
    }

    /*
    * 一级评论删除后，将一级评论的所有点赞删除，包括一级评论下所有二级评论即二级评论点赞
    * @msg 一级评论Id
    * @message 不知道
    * @channel 不知道
    * */
    @RabbitListener(queues = {RabbitConfig.ANSWER_COMMENT_LEVEL_ONE_QUEUE})
    public void listenAnswerLevelOneQueue(Long msg, Message message, Channel channel){
        answerCommentSupportDao.deleteAllAnswerCommentLvOneSupport(msg);
        List<Long> replyIds = answerCommentDao.getAnswerCommentLevelTwoIdByLv1Id(msg);
        replyIds.forEach(answerCommentService::deleteAnswerCommentLevelTwo);
    }

    /*
    * 二级评论删除后，将二级评论所有的点赞删除
    * */
    @RabbitListener(queues = {RabbitConfig.ANSWER_COMMENT_LEVEL_TWO_QUEUE})
    public void listenAnswerLevelTwoQueue(Long msg, Message message, Channel channel){
        answerCommentSupportDao.deleteAllAnswerCommentLvTwoSupport(msg);
    }

    @RabbitListener(queues = {RabbitConfig.ARTICLE_COMMENT_LEVEL_ONE_QUEUE})
    public void listenArticleLevelOneQueue(Long msg, Message message, Channel channel){
        articleCommentSupportDao.deleteAllLevelOneSupport(msg);
        List<Long> replyIds = articleCommentDao.getReplyIdsByCommentId(msg);
        replyIds.forEach(articleCommentService::deleteLevelTwo);
    }

    @RabbitListener(queues = {RabbitConfig.ARTICLE_COMMENT_LEVEL_TWO_QUEUE})
    public void listenArticleLevelTwoQueue(Long msg, Message message, Channel channel){
        articleCommentSupportDao.deleteAllLevelTwoSupport(msg);
    }
}
