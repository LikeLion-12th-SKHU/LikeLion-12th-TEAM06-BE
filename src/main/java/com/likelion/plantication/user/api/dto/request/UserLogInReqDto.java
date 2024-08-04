package com.likelion.plantication.user.api.dto.request;

public record UserLogInReqDto(
        String email,
        String password
) {
}
