package com.funeral.upload.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 空的PassWordEncoder
 * Attention: 这只是一个空的，配合Spring-security的Encoder。没有对密码进行加密
 *
 * @author FuneralObjects
 * CreateTime 2018/11/17 8:02 PM
 */
public class EmptyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }
}
