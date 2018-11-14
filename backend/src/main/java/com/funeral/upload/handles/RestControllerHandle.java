package com.funeral.upload.handles;

import com.funeral.upload.exception.RestControllerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 控制器handle
 *
 * @author FuneralObjects
 * CreateTime 2018/11/13 12:12 AM
 */
@RestControllerAdvice
public class RestControllerHandle {

    @Value("${internal_server_error}")
    private String INTERNAL_SERVER_ERROR;

    @ExceptionHandler(RestControllerException.class)
    public Object handle(RestControllerException re, HttpServletResponse response){
        String result = re.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR? INTERNAL_SERVER_ERROR :re.getMessage();
        try {
            response.sendError(re.getStatus().value(),result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
