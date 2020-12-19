package com.genehelix.utils;

import com.genehelix.entities.Customer;
import com.genehelix.entities.Employee;
import com.genehelix.interfaces.IEmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import java.util.List;

public class Util {

    public static String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashPassword = encoder.encode(password);
        System.out.println("Hashpassword=" + hashPassword);
        return hashPassword;
    }


}
