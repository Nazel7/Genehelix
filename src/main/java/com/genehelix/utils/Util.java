package com.genehelix.utils;

import com.genehelix.entities.Customer;
import com.genehelix.entities.Employee;
import com.genehelix.interfaces.IEmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Util {

    public static String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashPassword = encoder.encode(password);
        System.out.println("Hashpassword=" + hashPassword);
        return hashPassword;
    }


    public static byte[] formatFile(MultipartFile file) {
        try{
            return  file.getBytes();
        }catch (IOException e){
            e.getMessage();

            return null;
        }
    }

    public  static String fileConvertToString(MultipartFile file){

        String fileName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        return fileName;
    }
}
