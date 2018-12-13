package com.funeral.upload.handler;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出成功后处理的操作
 *
 * @author FuneralObjects
 * CreateTime 2018/11/18 10:01 AM
 */
public class SuccessLogoutHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.getWriter().printf("Success!");
    }
}
