package com.tcm.glossoscopy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//允许所有路径
                .allowedOrigins("*")//允许所有源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")//允许的HTTP方法
                .allowedHeaders("*")//允许所有请求头
                .allowCredentials(false)//不允许凭据（如果需要允许凭据，设置为true）
                .maxAge(3600);//预检请求缓存时间（单位：秒）
    }
}