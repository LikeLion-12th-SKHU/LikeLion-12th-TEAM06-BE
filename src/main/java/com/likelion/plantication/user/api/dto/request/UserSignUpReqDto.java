package com.likelion.plantication.user.api.dto.request;

import jakarta.validation.constraints.Pattern;

public record UserSignUpReqDto(
        @Pattern(
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "이메일 형식에 알맞지 않습니다."
        )
        String email,

        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,24}$",
                message = "비밀번호는 영문, 숫자, 특수문자 조합의 10자 이상 16자 이하여야 합니다."
        )
        String password,
        String rePassword,
        String phone,
        String name,
        String nickname,
        String profileImage
){
}
