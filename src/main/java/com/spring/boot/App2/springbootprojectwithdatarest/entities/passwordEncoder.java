package com.spring.boot.App2.springbootprojectwithdatarest.entities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class passwordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "gf123";
        System.out.println(encoder.encode(rawPassword));
    }
}