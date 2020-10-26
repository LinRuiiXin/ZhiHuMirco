package com.example.answer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/26 8:33 下午
 */
@Configuration
public class ThreadPoolConfig {
    @Bean
    public ExecutorService executorService(){
        return Executors.newFixedThreadPool(100);
    }
}
