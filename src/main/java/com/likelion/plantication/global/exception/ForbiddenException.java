package com.likelion.plantication.global.exception;

import com.likelion.plantication.global.exception.code.ErrorCode;

public class ForbiddenException extends CustomException {
    public ForbiddenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
