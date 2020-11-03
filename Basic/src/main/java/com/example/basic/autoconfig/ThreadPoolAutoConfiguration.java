package com.example.basic.autoconfig;

import com.example.basic.config.ThreadPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 * 线程池自动装配，默认分配100线程
 * @author LinRuiXin
 * @date 2020/10/31 3:41 下午
 */
@Configuration
@EnableConfigurationProperties(ThreadPoolConfig.class)
public class ThreadPoolAutoConfiguration {

    ThreadPoolConfig threadPoolConfig;

    @Autowired
    public ThreadPoolAutoConfiguration(ThreadPoolConfig threadPoolConfig) {
        this.threadPoolConfig = threadPoolConfig;
    }

    @ConditionalOnMissingBean(ExecutorService.class)
    @Bean
    public ExecutorService executorService(){
        return Executors.newFixedThreadPool(threadPoolConfig.coreThread);
    }
}
