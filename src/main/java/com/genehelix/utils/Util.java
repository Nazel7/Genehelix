package com.genehelix.utils;

import com.genehelix.entities.MedicalResultStatus;
import com.genehelix.entities.MedicalResult;
import com.genehelix.entities.UserResume;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
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

    public static byte[] formatFileTOByteArray(MultipartFile file) {
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

    public static void resumeDownloader(int resumeId, HttpServletResponse response,  UserResume userResume)
            throws IOException {
        if (userResume != null) {
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=" + userResume.getResumeName();

            response.setHeader(headerKey, headerValue);

            ServletOutputStream resumeOutputStream = response.getOutputStream();
            resumeOutputStream.write(userResume.getResume());

            resumeOutputStream.close();
        }

    }

    public static void medicalResultDownloader(int mrId, HttpServletResponse response, MedicalResult medicalResult)
            throws IOException {
        if (medicalResult != null) {
            response.setContentType("application/octet-stream");
            String headerValue = "attachment; filename=" + medicalResult.getName();
            String headerKey = "Content-Disposition";

            response.setHeader(headerKey, headerValue);

            ServletOutputStream mrOutputStream = response.getOutputStream();
            mrOutputStream.write(medicalResult.getMedicalResult());

            mrOutputStream.close();
        }

    }

    public static String checkFileNameError(MultipartFile file) throws NoSuchFileException {

        String fileName= Util.fileConvertToString(file);

        if(fileName.trim().isEmpty() || fileName.trim().contains(",") || fileName.trim().contains("..")){
            throw new NoSuchFileException("Not acceptable file format. file contains any of unacceptable char in filename." +
                    "Please edit filename");

        }else {
            return fileName;

        }

    }

    public static MedicalResultStatus setMR_status(MedicalResultStatus medicalResult_status){

        medicalResult_status.setStatus("YES");

        return medicalResult_status;
    }


}
