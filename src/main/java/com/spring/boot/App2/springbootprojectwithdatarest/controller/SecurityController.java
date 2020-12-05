package com.spring.boot.App2.springbootprojectwithdatarest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
    @GetMapping("/login-page")
    public String login(){

        return "fancy-login";
    }

    @GetMapping("/accessDeniedPage")
    public String accessDenied(){

        return "access-denied";
    }

}
