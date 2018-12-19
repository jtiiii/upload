package com.funeral.upload.entity;

import javax.validation.constraints.NotBlank;

/**
 * 登陆用户
 *
 * @author FuneralObjects
 * CreateTime 2018/6/5 2:29 PM
 */
public class LoginUser {
    @NotBlank(message = "username is required!")
    private String username;

    @NotBlank(message = "password is required!")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
