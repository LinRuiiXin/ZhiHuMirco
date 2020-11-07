package com.example.comment.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/5 4:40 下午
 */

@Configuration
public class RabbitConfig {

    public static final String COMMENT_EXCHANGE_TYPE = "direct";
    public static final String COMMENT_EXCHANGE = "COMMENT_EXCHANGE";
    public static final String COMMENT_LEVEL_ONE_QUEUE = "COMMENT_LEVEL_ONE_QUEUE";
    public static final String COMMENT_LEVEL_TWO_QUEUE = "COMMENT_LEVEL_TWO_QUEUE";

    /*
    * 声明一个持久化的路由交换机(根据路由键寻找相应队列，)
    * */
    @Bean(COMMENT_EXCHANGE)
    public Exchange commentExchange(){
        return ExchangeBuilder.directExchange(COMMENT_EXCHANGE).durable(true).build();
    }

    /*
    * 声明一个队列 名为 COMMENT_LEVEL_ONE_QUEUE
    * */
    @Bean(COMMENT_LEVEL_ONE_QUEUE)
    public Queue commentLevelOneQueue(){
        return new Queue(COMMENT_LEVEL_ONE_QUEUE);
    }

    /*
     * 声明一个队列 名为 COMMENT_LEVEL_TWO_QUEUE
     * */
    @Bean(COMMENT_LEVEL_TWO_QUEUE)
    public Queue commentLevelTwoQueue(){
        return new Queue(COMMENT_LEVEL_TWO_QUEUE);
    }

    /*
    * 将一级评论队列绑定到交换机 routing-key:队列名
    * */
    @Bean
    public Binding bindLv1QueueToExchange(@Qualifier(COMMENT_EXCHANGE) Exchange exchange,@Qualifier(COMMENT_LEVEL_ONE_QUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(COMMENT_LEVEL_ONE_QUEUE).noargs();
    }

    /*
     * 将二级评论队列绑定到交换机 routing-key:队列名
     * */
    @Bean
    public Binding bindLv2QueueToExchange(@Qualifier(COMMENT_EXCHANGE) Exchange exchange,@Qualifier(COMMENT_LEVEL_TWO_QUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(COMMENT_LEVEL_TWO_QUEUE).noargs();
    }

}
