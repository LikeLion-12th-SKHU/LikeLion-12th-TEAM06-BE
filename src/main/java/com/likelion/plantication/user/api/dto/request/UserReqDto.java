package com.likelion.plantication.user.api.dto.request;

public record UserReqDto(
        String email,
        String password,
        String phone,
        String name,
        String nickname,
        String profileImage,
        String role
){
}
