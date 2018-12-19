package com.funeral.upload.security;


/**
 * 跨域使用的token
 *
 * @author FuneralObjects
 * CreateTime 2018/11/17 6:35 PM
 */
public class Token{
    private String account;
    private String token;

    public Token(String account, String token) {
        this.account = account;
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public String getToken() {
        return token;
    }
}
