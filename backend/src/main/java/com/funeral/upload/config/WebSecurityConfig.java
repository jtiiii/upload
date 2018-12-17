package com.funeral.upload.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.annotation.Resource;

/**
 * @author FuneralObjects
 * CreateTime 2018/11/11 11:17 PM
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userService;
    @Resource
    private PasswordEncoder emptyPasswordEncoder;
    @Resource
    private AuthenticationSuccessHandler successHandler;
    @Resource
    private AuthenticationFailureHandler failureHandler;
    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;
    @Resource
    private CorsConfigurationSource corsConfigurationSource;
    @Resource
    private AccessDeniedHandler accessDeniedHandler;
    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                //自定用户匹配逻辑+自定密码处理
                .userDetailsService(userService).passwordEncoder(emptyPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    //对files请求不进行拦截
                    .antMatchers("/files/**").permitAll()
                    //对所有请求配置
                    .anyRequest()
                    //开启认证
                    .authenticated()
                    .and()
                //开启跨域请求
                .cors()
                    .configurationSource(corsConfigurationSource)
                    .and()
                //对于表单登陆请求配置
                .formLogin()
                    //form表单中action提交地址
                    .loginProcessingUrl("/login")
                    //登陆成功后的处理
                    .successHandler(successHandler)
                    //登陆失败的处理
                    .failureHandler(failureHandler)
                    .and()
                //关于登出的配置
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(logoutSuccessHandler)
                    //logout对对所有用户
                    .permitAll()
                    .and()
                //对CSRF 远程防御的配置
                .csrf()
//                .disable()
                    //login地址不进行远程防御
                    .ignoringAntMatchers("/login","/logout")
                    //使用cookie方式进行token配置
//                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and()
                //发生异常时的处理
                .exceptionHandling()
                    //认证异常处理
                    .authenticationEntryPoint(authenticationEntryPoint)
                    //拒绝访问异常处理
                    .accessDeniedHandler(accessDeniedHandler);
    }


}
