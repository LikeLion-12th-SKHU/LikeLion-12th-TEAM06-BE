package com.likelion.plantication.global.exception.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 400
    PASSWORD_NOT_MATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    INVALID_EMAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 이메일 형식입니다."),
    INVALID_PASSWORD_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 형식입니다."),
    INVALID_NICKNAME_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 닉네임 형식입니다."),
    INVALID_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 토큰입니다."),

    // 403
    AUTH_FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, "권한이 제한된 토큰입니다."),
    EXPIRED_TOKEN_EXCEPTION(HttpStatus.FORBIDDEN, "토큰이 만료되었습니다."),
    ACCESS_DENIED_EXCEPTION(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    AUTHENTICATION_FAILED_EXCEPTION(HttpStatus.FORBIDDEN, "인증에 실패했습니다."),


    // 404
    EMAIL_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 이메일을 찾을 수 없습니다."),
    ID_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 ID를 찾을 수 없습니다."),
    USER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),

    DIARY_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 일기가 없습니다."),
    COMMENT_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 댓글이 없습니다."),
    LIKE_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 좋아요가 없습니다."),
    GUIDE_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 도감이 없습니다."),

    // 409 CONFLICT
    LIKE_ALREADY_EXISTS_EXCEPTION(HttpStatus.CONFLICT, "해당 일기의 좋아요가 이미 존재합니다."),

    // 500
    CREATE_TOKEN_FAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "에러로 인해 토큰을 생성할 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러가 발생했습니다."),

    // 503
    FAIL_GET_OAUTH_TOKEN(HttpStatus.SERVICE_UNAVAILABLE, "토큰을 가져오는데 실패했습니다.");


    private final HttpStatus httpStatus;
    private final String message;
    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
