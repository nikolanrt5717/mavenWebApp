package com.msg.laza.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleException(Exception ex){
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,ex.getMessage(),ex);
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<Object> handleCustomException(CustomException ex){
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,ex.getMessage(),ex);
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }
}
