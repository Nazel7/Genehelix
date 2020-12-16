package com.spring.boot.App2.springbootprojectwithdatarest.securityConfig.datasourceSecurityConfig;

import com.spring.boot.App2.springbootprojectwithdatarest.appServices.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@Primary
public class WebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {
    @Autowired
   private SimpleAuthenticationSuccessfulHandler successfulHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());

    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home-page").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/company-employees/employee-list", "/company-employees/**").hasRole("ADMIN")
                .antMatchers("/customer-page", "/customer-page/**").hasRole("CUSTOMER")
                .antMatchers( "/company-employees/**").hasAnyRole("ADMIN", "EMPLOYEE")
                .and()
                .formLogin()
                .loginPage("/login-page")
                .loginProcessingUrl("/loginProcessing")
                .permitAll()
                .successHandler(successfulHandler)
                .and()
                .logout()
                .logoutSuccessUrl("/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDeniedPage");

    }

}