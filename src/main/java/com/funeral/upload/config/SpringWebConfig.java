package com.funeral.upload.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;

/**
 * Spring-Web-mvc的配置类
 * 相当于spring-mvc.xml
 * @author FuneralObjects
 * CreateTime 2018/11/12 6:10 PM
 */
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan( "com.funeral.upload.controller.**" )
public class SpringWebConfig {

    @Bean
    public CommonsMultipartResolver multipartResolver(ServletContext context){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(context);
        resolver.setMaxUploadSize(10485760);
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }
}
