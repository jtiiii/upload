package com.funeral.upload.aspect;

import com.funeral.upload.exception.RestControllerException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 对RestController的统一处理
 *
 * @author FuneralObjects
 * CreateTime 2018/11/13 7:52 PM
 */
@Component
@Aspect
public class RestControllerAspect {



    @Around( "@target(org.springframework.web.bind.annotation.RestController) && execution(* com.funeral.upload.controller.*.*(..)) && args(..,bindingResult ) )")
    public Object bingResultHandle (ProceedingJoinPoint joinPoint, BindingResult bindingResult) throws RestControllerException {
        if(bindingResult.hasErrors()){
            StringBuilder errorMeg = new StringBuilder();
            for(FieldError error:bindingResult.getFieldErrors()){
                errorMeg.append(error.getDefaultMessage()).append("\n");
            }
            throw new RestControllerException(errorMeg.toString(),HttpStatus.BAD_REQUEST);
        }
        try {
            return joinPoint.proceed();
        } catch (RestControllerException rce){
            throw rce;
        } catch (Throwable e){
            throw new RestControllerException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @AfterReturning( value = "@target(org.springframework.web.bind.annotation.RestController) && execution(* com.funeral.upload.controller.*.*(..))", returning = "result")
    public Object nullResultHandle(Object result) throws RestControllerException{
        if(result == null){
            throw new RestControllerException(HttpStatus.NO_CONTENT);
        }
        return result;
    }
}
