package com.funeral.upload.controller;

import com.funeral.upload.security.Token;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author FuneralObjects
 * CreateTime 2018/12/17 10:08 PM
 */
@RestController
@RequestMapping("/check")
public class CheckController {

    @GetMapping("/login")
    public Token checkLogin(HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        return new Token(auth.getName()+"测试",token.getToken());
    }
}
