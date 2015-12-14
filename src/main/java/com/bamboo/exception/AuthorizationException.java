package com.bamboo.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Bob Guan on 2015/12/13.
 */
public class AuthorizationException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        try {
            httpServletResponse.sendRedirect("/exception");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return new ModelAndView("index");
    }
}
