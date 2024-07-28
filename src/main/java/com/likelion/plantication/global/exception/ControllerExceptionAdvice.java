package com.likelion.plantication.global.exception;

import com.likelion.plantication.global.exception.code.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<String> handleCustomException(CustomException e) {
        String errorMessage = String.format("Error: %s, Message: %s", e.getErrorCode().getMessage(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus())
                .body(errorMessage);
    }

    public <T> ResponseEntity<String> handleSuccess(SuccessCode successCode, T e) {
        String successMessage = String.format("Success: %s, Message: %s", successCode.getMessage(), e.toString());
        return ResponseEntity.status(successCode.getHttpStatusCode())
                .body(successMessage);
    }
}
