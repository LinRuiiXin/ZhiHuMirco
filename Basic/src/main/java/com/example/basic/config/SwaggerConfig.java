package com.example.basic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/10/31 3:59 下午
 */
//与zhi-hu.swagger为前缀的 spring 配置类，可在 application.properties 或 application.yml 中进行属性配置
@ConfigurationProperties(prefix = "zhi-hu.swagger")
public class SwaggerConfig {
    //默认命名为 服务名+"接口文档"
    @Value("${spring.application.name}接口文档")
    //默认命名为 服务名+"接口文档"
    public String title;
    @Value("${spring.application.name}接口文档")
    public String description;
    public String termsOfServiceUrl = "";
    //默认扫描
    @Value("com.example.#{'${spring.application.name}'.toLowerCase()}")
    public String scanPath;
    @Value("1.0.0")
    public String version;

    public SwaggerConfig() {

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getScanPath() {
        return scanPath;
    }

    public void setScanPath(String scanPath) {
        this.scanPath = scanPath;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
