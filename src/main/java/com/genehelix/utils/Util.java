package com.genehelix.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Util {

    public static String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashPassword = encoder.encode(password);
        System.out.println("Hashpassword=" + hashPassword);
        return hashPassword;
    }
}
