package com.example.comment.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 * 该配置文件声明了一些 MQ 以及 Exchange，主要用于异步执行 "删除一级评论" 需要删除的二级评论及点赞，以及 "删除二级评论" 需要删除的点赞
 * @author LinRuiXin
 * @date 2020/11/5 4:40 下午
 */

@Configuration
public class RabbitConfig {

    public static final String COMMENT_EXCHANGE_TYPE = "direct";
    public static final String COMMENT_EXCHANGE = "COMMENT_EXCHANGE";
    public static final String ANSWER_COMMENT_LEVEL_ONE_QUEUE = "ANSWER_COMMENT_LEVEL_ONE_QUEUE";
    public static final String ANSWER_COMMENT_LEVEL_TWO_QUEUE = "ANSWER_COMMENT_LEVEL_TWO_QUEUE";
    public static final String ARTICLE_COMMENT_LEVEL_ONE_QUEUE = "ARTICLE_COMMENT_LEVEL_ONE_QUEUE";
    public static final String ARTICLE_COMMENT_LEVEL_TWO_QUEUE = "ARTICLE_COMMENT_LEVEL_TWO_QUEUE";

    /*
    * 声明一个持久化的路由交换机(根据路由键寻找相应队列，)
    * */
    @Bean(COMMENT_EXCHANGE)
    public Exchange commentExchange(){
        return ExchangeBuilder.directExchange(COMMENT_EXCHANGE).durable(true).build();
    }

    /*
    * 声明一个队列 名为 ANSWER_COMMENT_LEVEL_ONE_QUEUE
    * */
    @Bean(ANSWER_COMMENT_LEVEL_ONE_QUEUE)
    public Queue answerCommentLevelOneQueue(){
        return new Queue(ANSWER_COMMENT_LEVEL_ONE_QUEUE);
    }

    /*
     * 声明一个队列 名为 COMMENT_LEVEL_TWO_QUEUE
     * */
    @Bean(ANSWER_COMMENT_LEVEL_TWO_QUEUE)
    public Queue answerCommentLevelTwoQueue(){
        return new Queue(ANSWER_COMMENT_LEVEL_TWO_QUEUE);
    }

    /*
    * 将一级评论队列绑定到交换机 routing-key:队列名
    * */
    @Bean
    public Binding bindLv1QueueToExchange(@Qualifier(COMMENT_EXCHANGE) Exchange exchange,@Qualifier(ANSWER_COMMENT_LEVEL_ONE_QUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(ANSWER_COMMENT_LEVEL_ONE_QUEUE).noargs();
    }

    /*
     * 将二级评论队列绑定到交换机 routing-key:队列名
     * */
    @Bean
    public Binding bindLv2QueueToExchange(@Qualifier(COMMENT_EXCHANGE) Exchange exchange,@Qualifier(ANSWER_COMMENT_LEVEL_TWO_QUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(ANSWER_COMMENT_LEVEL_TWO_QUEUE).noargs();
    }

    /*
    * 声明一个队列 名为 ARTICLE_COMMENT_LEVEL_ONE_QUEUE
    * */
    @Bean(ARTICLE_COMMENT_LEVEL_ONE_QUEUE)
    public Queue articleCommentLevelOneQueue(){
        return new Queue(ARTICLE_COMMENT_LEVEL_ONE_QUEUE);
    }

    /*
     * 声明一个队列 名为 ARTICLE_COMMENT_LEVEL_TWO_QUEUE
     * */
    @Bean(ARTICLE_COMMENT_LEVEL_TWO_QUEUE)
    public Queue articleCommentLevelTwoQueue(){
        return new Queue(ARTICLE_COMMENT_LEVEL_TWO_QUEUE);
    }

    /*
     * 将一级评论队列绑定到交换机 routing-key:队列名
     * */
    @Bean
    public Binding bindArticleLv1ToExchange(@Qualifier(COMMENT_EXCHANGE)Exchange exchange,@Qualifier(ARTICLE_COMMENT_LEVEL_ONE_QUEUE)Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(ARTICLE_COMMENT_LEVEL_ONE_QUEUE).noargs();
    }

    /*
     * 将二级评论队列绑定到交换机 routing-key:队列名
     * */
    @Bean
    public Binding bindArticleLv2ToExchange(@Qualifier(COMMENT_EXCHANGE)Exchange exchange,@Qualifier(ARTICLE_COMMENT_LEVEL_TWO_QUEUE)Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(ARTICLE_COMMENT_LEVEL_TWO_QUEUE).noargs();
    }

}
