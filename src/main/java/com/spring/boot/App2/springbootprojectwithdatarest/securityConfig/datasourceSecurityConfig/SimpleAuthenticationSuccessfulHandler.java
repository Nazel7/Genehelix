package com.spring.boot.App2.springbootprojectwithdatarest.securityConfig.datasourceSecurityConfig;

import com.spring.boot.App2.springbootprojectwithdatarest.entity.MyUserDetails;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class SimpleAuthenticationSuccessfulHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse,
                                            "/dashboard");

    }


}
