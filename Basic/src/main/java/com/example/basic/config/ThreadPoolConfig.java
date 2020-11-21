package com.example.basic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/31 3:43 下午
 */

@ConfigurationProperties(prefix = "zhi-hu.thread-pool")
public class ThreadPoolConfig {
    public int coreThread;

    public ThreadPoolConfig(){
        this(5);
    }

    public ThreadPoolConfig(int coreThread) {
        this.coreThread = coreThread;
    }

    public int getCoreThread() {
        return coreThread;
    }

    public void setCoreThread(int coreThread) {
        this.coreThread = coreThread;
    }
}
