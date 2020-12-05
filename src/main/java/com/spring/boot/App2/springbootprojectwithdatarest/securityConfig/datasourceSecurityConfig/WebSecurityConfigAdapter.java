package com.spring.boot.App2.springbootprojectwithdatarest.securityConfig.datasourceSecurityConfig;

import com.spring.boot.App2.springbootprojectwithdatarest.appServiceDAO.UserServiceImpl;
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
                .antMatchers("/customer-page").hasRole("CUSTOMER")
                .antMatchers("/employee-page").hasRole("EMPLOYEE")
                .antMatchers("/company-employees/employee-list").hasRole("ADMIN")
                .antMatchers("/company-employees/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login-page")
                .loginProcessingUrl("/loginProcessing")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDeniedPage");

    }

}