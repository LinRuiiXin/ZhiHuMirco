package com.example.basic.autoconfig;

import com.example.basic.config.SwaggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * TODO
 * 自动配置 Swagger 环境，默认标题使用服务名，默认扫描 com.example下以服务名小写取的基础包。
 * @author LinRuiXin
 * @date 2020/10/31 3:59 下午
 */

@Configuration
//使用属性配置类 SwaggerConfig
@EnableConfigurationProperties(SwaggerConfig.class)
//开启Swagger2
@EnableSwagger2
public class SwaggerAutoConfiguration {

    SwaggerConfig swaggerConfig;

    @Autowired
    public SwaggerAutoConfiguration(SwaggerConfig swaggerConfig) {
        this.swaggerConfig = swaggerConfig;
    }

    //如果环境中有此类对象，那么不装配
    @ConditionalOnMissingBean(Docket.class)
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerConfig.getScanPath()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerConfig.getTitle())
                .description(swaggerConfig.getDescription())
                .version(swaggerConfig.getVersion())
                .termsOfServiceUrl(swaggerConfig.getTermsOfServiceUrl())
                .build();
    }

}
