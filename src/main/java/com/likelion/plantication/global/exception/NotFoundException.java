package com.likelion.plantication.global.exception;

import com.likelion.plantication.global.exception.code.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
