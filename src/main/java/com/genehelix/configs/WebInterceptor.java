package com.genehelix.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInterceptor implements WebMvcConfigurer {
    @Autowired
    LoginInterception loginInterception;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterception).addPathPatterns("/**")
                .excludePathPatterns("/css/*", "/fancy-login.html", "/js/**", "/fonts/**", "/img/**", "/logo/**",
                        "/font-awesome.fonts/**", "/", "/login");
    }
}
