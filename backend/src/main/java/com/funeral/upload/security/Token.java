package com.funeral.upload.security;

import org.springframework.security.web.csrf.CsrfToken;

/**
 * 跨域使用的token
 *
 * @author FuneralObjects
 * CreateTime 2018/11/17 6:35 PM
 */
public class Token implements CsrfToken {
    private String headerName;
    private String parameterName;

    public Token(String headerName, String parameterName) {
        this.headerName = headerName;
        this.parameterName = parameterName;
    }

    @Override
    public String getHeaderName() {
        return this.headerName;
    }

    @Override
    public String getParameterName() {
        return this.parameterName;
    }

    @Override
    public String getToken() {
        return this.parameterName;
    }
}
