package com.funeral.upload.filter;

import com.funeral.upload.controller.LoginController;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 登陆校验过滤
 *
 * @author FuneralObjects 张峰
 * CreateTime 2018/6/5 3:13 PM
 */
@Component
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("login-token");
        if(request.getMethod().equals("OPTIONS")){
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
