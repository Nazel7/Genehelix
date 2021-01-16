package com.genehelix.exceptions;

import com.genehelix.dtos.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorException {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> responseClassResponseEntity(NotFundException exc) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorStatus("" + HttpStatus.NOT_FOUND.value());
        errorResponse.setErrorMessage(exc.getMessage());
        errorResponse.setTimeLapse("" + System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> responseClassResponseEntity(Exception exc) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorStatus("" + HttpStatus.BAD_REQUEST.value());
        errorResponse.setErrorMessage(exc.getMessage());
        errorResponse.setTimeLapse("" + System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
