package com.genehelix.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class CustomerController {

    @GetMapping("/customer")
    public String customerPage(){

        return "customer-page";
    }
}
