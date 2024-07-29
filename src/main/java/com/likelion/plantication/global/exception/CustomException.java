package com.likelion.plantication.global.exception;

import com.likelion.plantication.global.exception.code.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getHttpStatus() {
        return errorCode.getHttpStatusCode();
    }

    public String getMessage() {
        return errorCode.getMessage();
    }
}