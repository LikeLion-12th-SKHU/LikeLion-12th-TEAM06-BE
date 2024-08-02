package com.likelion.plantication.global.exception;

import com.likelion.plantication.global.exception.code.ErrorCode;

public class AlreadyExistsException extends CustomException{
    public AlreadyExistsException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
