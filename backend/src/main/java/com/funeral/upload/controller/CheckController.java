package com.funeral.upload.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FuneralObjects
 * CreateTime 2018/12/17 10:08 PM
 */
@RestController
@RequestMapping("/check")
public class CheckController {

    @GetMapping("/login")
    public String checkLogin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails details = (UserDetails) auth.getPrincipal();
        return details.getUsername();
    }
}
