package com.funeral.upload.filter;

import com.funeral.upload.controller.LoginController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆校验过滤
 *
 * @author FuneralObjects 张峰
 * CreateTime 2018/6/5 3:13 PM
 */
public class LoginFilter implements Filter {



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("login-token");
        if(HttpMethod.resolve(request.getMethod()) == HttpMethod.OPTIONS){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        if(LoginController.isLogin(token)){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.sendError(4033,"This token is blank!");
    }

    @Override
    public void destroy() {

    }
}
