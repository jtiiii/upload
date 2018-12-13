package com.funeral.upload.config;

import com.funeral.upload.handler.DeniedAccessHandler;
import com.funeral.upload.handler.FailAuthenticationHandler;
import com.funeral.upload.handler.SuccessAuthenticationHandler;
import com.funeral.upload.handler.SuccessLogoutHandler;
import com.funeral.upload.resolver.UploadStateMultipartResolver;
import com.funeral.upload.security.EmptyPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;

/**
 * Spring-Web-mvc的配置类
 * 相当于spring-mvc.xml
 * @author FuneralObjects
 * CreateTime 2018/11/12 6:10 PM
 */
@Configuration
@EnableWebMvc
@ComponentScan( "com.funeral.upload.controller" )
public class SpringWebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowCredentials(true)
//                .allowedHeaders("*")
//                .allowedMethods("*")
//                .allowedOrigins("http://localhost:8081").maxAge(10800);
//    }

//    @Bean
//    public CorsFilter corsFilter(){
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedMethod("*");
//        configuration.addAllowedHeader("*");
//        configuration.setAllowCredentials(true);
//        configuration.addAllowedOrigin("http://localhost:8081");
//        source.registerCorsConfiguration("/**",configuration);
//        return new CorsFilter(source);
//    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:8080");
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(ServletContext context){
        CommonsMultipartResolver resolver = new UploadStateMultipartResolver(context);
        //上传最大限制
//        resolver.setMaxUploadSize(10485760);
        //上传文件编码
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }

    @Bean
    protected PasswordEncoder emptyPasswordEncoder(){
        return new EmptyPasswordEncoder();
    }
    @Bean
    protected AuthenticationSuccessHandler successHandler(){
        return new SuccessAuthenticationHandler();
    }
    @Bean
    protected AuthenticationFailureHandler failureHandler(){
        return new FailAuthenticationHandler();
    }
    @Bean
    protected LogoutSuccessHandler logoutSuccessHandler(){
        return new SuccessLogoutHandler();
    }
    @Bean
    protected AccessDeniedHandler accessDeniedHandler(){
        return new DeniedAccessHandler();
    }
    @Bean
    protected AuthenticationEntryPoint authenticationEntryPoint(){
        return new Http403ForbiddenEntryPoint();
    }

}
