package com.funeral.upload.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funeral.upload.security.Token;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 校验成功的处理操作
 *
 * @author FuneralObjects
 * CreateTime 2018/11/17 8:32 PM
 */
public class SuccessAuthenticationHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //认证成功了，就返回一个CSRF的token
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        response.getWriter().print(new ObjectMapper().writeValueAsString(new Token(authentication.getName(),token.getToken())));
    }
}
