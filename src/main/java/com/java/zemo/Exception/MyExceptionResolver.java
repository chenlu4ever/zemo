package com.java.zemo.Exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 10450
 * @description TODO
 * @date 2021/7/23 14:13
 */
public class MyExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if (e instanceof UnauthorizedException) {
            ModelAndView mv = new ModelAndView();
            MappingJackson2JsonView view = new MappingJackson2JsonView();
            mv.setView(view);
            mv.addObject("status", "fail");
            mv.addObject("message", "系统错误");
            return mv;
        }
        return null;
    }

}

