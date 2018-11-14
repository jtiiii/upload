package com.funeral.upload.exception;

import org.springframework.http.HttpStatus;

/**
 * 这是restful 控制器的专用异常
 *
 * @author FuneralObjects
 * CreateTime 2018/11/13 12:18 AM
 */
public class RestControllerException extends RuntimeException {
    private HttpStatus status;

    public RestControllerException(HttpStatus status) {
        this.status = status;
    }

    public RestControllerException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public RestControllerException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public RestControllerException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    public RestControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
