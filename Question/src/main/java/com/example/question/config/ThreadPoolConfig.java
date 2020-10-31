package com.example.question.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/27 8:46 下午
 */

@EnableAsync
@Configuration
public class ThreadPoolConfig {
    @Bean
    public ExecutorService executorService(){
        return Executors.newFixedThreadPool(100);
    }
}
