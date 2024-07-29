package com.likelion.plantication.global.exception.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {
    // 200
    GET_SUCCESS(HttpStatus.OK, "조회에 성공했습니다."),
    USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인 되었습니다."),

    // 201
    USER_CREATE_SUCCESS(HttpStatus.CREATED, "회원가입 되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
