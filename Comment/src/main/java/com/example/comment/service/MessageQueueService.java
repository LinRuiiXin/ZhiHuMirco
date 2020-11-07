package com.example.comment.service;

import com.example.comment.config.RabbitConfig;
import com.example.comment.dao.CommentDao;
import com.example.comment.dao.CommentSupportDao;
import com.example.comment.service.interfaces.CommentService;
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
    private final CommentDao commentDao;
    private final CommentSupportDao commentSupportDao;
    private final CommentService commentService;

    @Autowired
    public MessageQueueService(AmqpTemplate amqpTemplate, CommentDao commentDao, CommentSupportDao commentSupportDao, CommentService commentService) {
        this.amqpTemplate = amqpTemplate;
        this.commentDao = commentDao;
        this.commentSupportDao = commentSupportDao;
        this.commentService = commentService;
    }

    /*
    * 一级评论删除后，将一级评论的所有点赞删除，包括一级评论下所有二级评论即二级评论点赞
    * @msg 一级评论Id
    * @message 不知道
    * @channel 不知道
    * */
    @RabbitListener(queues = {RabbitConfig.COMMENT_LEVEL_ONE_QUEUE})
    public void listenCommentLevelOneQueue(Long msg, Message message, Channel channel){
        commentSupportDao.deleteAllAnswerCommentLvOneSupport(msg);
        List<Long> replyIds = commentDao.getAnswerCommentLevelTwoIdByLv1Id(msg);
        replyIds.forEach(commentService::deleteAnswerCommentLevelTwo);
    }

    /*
    * 二级评论删除后，将二级评论所有的点赞删除
    * */
    @RabbitListener(queues = {RabbitConfig.COMMENT_LEVEL_TWO_QUEUE})
    public void listenCommentLevelTwoQueue(Long msg, Message message, Channel channel){
        commentSupportDao.deleteAllAnswerCommentLvTwoSupport(msg);
    }
}
