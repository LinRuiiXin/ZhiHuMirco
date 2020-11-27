package com.example.search.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/27 8:02 下午
 */
@Configuration
public class GsonConfig {

    @Bean
    public Gson gson(){
        return new Gson();
    }
}
