package com.funeral.upload.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 允许跨域请求Files文件夹
 * @author FuneralObjects 张峰
 * CreateTime 2018/6/5 6:43 PM
 */
public class FilesCorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Headers","*");
        response.setHeader("Access-Control-Allow-Methods","GET, PUT, DELETE, POST");
        response.addHeader("Access-Control-Allow-Origin", "*");

        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
