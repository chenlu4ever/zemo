package com.java.zemo.Exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 10450
 * @description TODO
 * @date 2021/7/23 15:03
 */
@ControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public String unAuth(AuthorizationException e){
        return "noAuth:"+e.getMessage();
    }
}

