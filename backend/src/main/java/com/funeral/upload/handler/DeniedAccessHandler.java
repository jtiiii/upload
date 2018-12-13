package com.funeral.upload.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问权限禁止的自定义处理
 *
 * @author FuneralObjects
 * CreateTime 2018/11/18 10:16 PM
 */
public class DeniedAccessHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendError(403,"You are not having this resource access permission.");
    }
}
