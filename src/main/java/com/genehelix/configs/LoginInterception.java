package com.genehelix.configs;

import com.genehelix.Genehelix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterception extends HandlerInterceptorAdapter {
    public static final Logger logger=  LoggerFactory.getLogger(Genehelix.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String result= request.getMethod();
        logger.info(result + " intercepted");
        return true;
    }


}
