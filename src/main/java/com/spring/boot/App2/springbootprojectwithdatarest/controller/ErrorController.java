package com.spring.boot.App2.springbootprojectwithdatarest.controller;

import com.spring.boot.App2.springbootprojectwithdatarest.entity.DataNotFund;
import com.spring.boot.App2.springbootprojectwithdatarest.entity.ErrorResponseClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseClass> responseClassResponseEntity(DataNotFund exc){
        ErrorResponseClass errorResponseClass= new ErrorResponseClass();
        errorResponseClass.setErrorStatus(""+ HttpStatus.NOT_FOUND.value());
        errorResponseClass.setErrorMessage("kindly press back to the previous page list");
        errorResponseClass.setTimeLapse(""+System.currentTimeMillis());

        return new ResponseEntity<>(errorResponseClass, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseClass> responseClassResponseEntity(Exception exc){
        ErrorResponseClass errorResponseClass= new ErrorResponseClass();
        errorResponseClass.setErrorStatus(""+ HttpStatus.BAD_REQUEST.value());
        errorResponseClass.setErrorMessage("kindly press back to the previous page list! Note: only add review to " +
                "customer and add customer to employee ");
        errorResponseClass.setTimeLapse(""+System.currentTimeMillis());

        return new ResponseEntity<>(errorResponseClass, HttpStatus.BAD_REQUEST);
    }
}
