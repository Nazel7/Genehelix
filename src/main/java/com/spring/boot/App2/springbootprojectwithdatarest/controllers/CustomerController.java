package com.spring.boot.App2.springbootprojectwithdatarest.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class CustomerController {

    @GetMapping("/customer")
    public String customerPage(){

        return "customer-page";
    }
}
