package com.likelion.plantication.global.exception.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 403
    FORBIDDEN_AUTH_EXCEPTION(HttpStatus.FORBIDDEN, "권한이 없는 토큰입니다."),
    EXPIRED_TOKEN_EXCEPTION(HttpStatus.FORBIDDEN, "토큰이 만료되었습니다."),
    ACCESS_DENIED_EXCEPTION(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    AUTHENTICATION_FAILED_EXCEPTION(HttpStatus.FORBIDDEN, "인증에 실패했습니다."),

    // 404 NOT FOUND
    USER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 사용자가 없습니다."),
    DIARY_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 일기가 없습니다."),
    COMMENT_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 댓글이 없습니다."),
    LIKE_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 좋아요가 없습니다."),
    GUIDE_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 도감이 없습니다."),
    INQUIRY_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 문의가 없습니다."),

    // 409 CONFLICT
    LIKE_ALREADY_EXISTS_EXCEPTION(HttpStatus.CONFLICT, "해당 일기의 좋아요가 이미 존재합니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러가 발생했습니다");

    private final HttpStatus httpStatus;
    private final String message;
    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
