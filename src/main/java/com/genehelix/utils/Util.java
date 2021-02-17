package com.genehelix.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

public class Util {

    public static String hashPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashPassword = encoder.encode(password);
        System.out.println("Hashpassword=" + hashPassword);
        return hashPassword;
    }

    public static boolean decodePassword(String rawPassword, String encodedPassword){

        BCryptPasswordEncoder decoder = new BCryptPasswordEncoder();

        return  decoder.matches(rawPassword, encodedPassword);

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

    public static String fileCovertToImageBase64String(MultipartFile file) throws IOException {

        String base64String= Base64.getEncoder().encodeToString(file.getBytes());

        return base64String;
    }

    public static boolean compareString(String string1, String string2) {


        return string1.toLowerCase().trim().equals(string2.toLowerCase().trim());
    }
}
