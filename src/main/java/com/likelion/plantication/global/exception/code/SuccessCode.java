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
    TOKEN_GET_SUCCESS(HttpStatus.OK, "토큰 조회에 성공했습니다."),

    // user
    USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인 되었습니다."),
  
    PATCH_UPDATE_SUCCESS(HttpStatus.OK, "성공적으로 수정되었습니다."),
    DELETE_SUCCESS(HttpStatus.OK, "성공적으로 삭제되었습니다."),

    // challenge
    CHALLENGE_GET_SUCCESS(HttpStatus.OK, "챌린지 조회에 성공했습니다."),
    CHALLENGE_UPDATE_SUCCESS(HttpStatus.CREATED, "챌린지가 수정되었습니다."),

    // group
    GROUP_GET_SUCCESS(HttpStatus.OK, "그룹 조회에 성공했습니다."),
    GROUP_UPDATE_SUCCESS(HttpStatus.CREATED, "그룹이 수정되었습니다."),

    // 201
    USER_CREATE_SUCCESS(HttpStatus.CREATED, "회원가입 되었습니다."),

    CHALLENGE_CREATE_SUCCESS(HttpStatus.CREATED, "챌린지가 작성되었습니다."),

    GROUP_CREATE_SUCCESS(HttpStatus.CREATED, "그룹이 작성되었습니다."),

    // 204
    CHALLENGE_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "챌린지를 삭제했습니다."),

    GROUP_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "그룹을 삭제했습니다."),

    POST_SAVE_SUCCESS(HttpStatus.CREATED, " 성공적으로 등록되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
