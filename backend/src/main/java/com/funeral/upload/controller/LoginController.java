package com.funeral.upload.controller;

import com.funeral.upload.entity.LoginUser;
import com.funeral.upload.service.UserServiceImpl;
import com.funeral.upload.util.TimeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 登陆controller
 *
 * @author FuneralObjects 张峰
 * CreateTime 2018/6/5 2:26 PM
 */
@RestController
@RequestMapping("/login-test")
public class LoginController {
    /**
     * 在线用户
     */
    private static final Map<String,LoginUser> ONLINE_USER = new ConcurrentHashMap<>();

    /**
     * 用户登陆时间
     */
    private static final Map<String,Long> USER_LOGIN_AT = new ConcurrentHashMap<>();
    /**
     * token 到期时间
     */
    private static final Long EXPIRE_TIME = 1200000L;

    @PostMapping
    public String login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
        LoginUser user = UserServiceImpl.findUser(loginUser.getUsername());
        if(user == null || !user.getPassword().equals( loginUser.getPassword() )){
            return null;
        }
        String token = newToken();
        login(token,user);
        return token;
    }



    public static void refreshExpiredTime(String token){
        if(USER_LOGIN_AT.containsKey(token)){
            USER_LOGIN_AT.put(token,TimeUtils.getNow());
        }
    }


    @PostConstruct
    private void init(){
        Thread validate = new Thread( () -> validateLoginUserExpireTime());
        validate.start();
    }

    public static boolean isLogin(String token){
        if(token != null && ONLINE_USER.containsKey(token)){
            refreshExpiredTime(token);
            return true;
        }
        return false;
    }

    private void validateLoginUserExpireTime(){
        Long now = TimeUtils.getNow();
        Set<String> expiredToken = new HashSet<>();
        for(String token:USER_LOGIN_AT.keySet()){
            if(USER_LOGIN_AT.get( token ).compareTo( now - EXPIRE_TIME ) > 0){
                continue;
            }
            expiredToken.add(token);
        }
        //对过期的进行登出操作
        for(String expired: expiredToken){
            logout(expired);
        }
        try {
            Thread.sleep(30000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static String newToken(){
        return UUID.randomUUID().toString();
    }

    private static void login(String token,LoginUser user){
        USER_LOGIN_AT.put(token,TimeUtils.getNow());
        ONLINE_USER.put(token,user);
    }

    private static void logout(String token){
        USER_LOGIN_AT.remove(token);
        ONLINE_USER.remove(token);
    }
}
