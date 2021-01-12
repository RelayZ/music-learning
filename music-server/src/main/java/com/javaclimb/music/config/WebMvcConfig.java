package com.javaclimb.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //所有目录
//                .allowedOrigins("*")  老版本的
                .allowedMethods("*")
                .allowedOriginPatterns("*")//允许哪些网站
                .allowCredentials(true); //是否需要验证
    }
}
