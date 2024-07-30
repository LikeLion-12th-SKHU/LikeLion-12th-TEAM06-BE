package com.likelion.plantication.user.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserSignUpReqDto(

        @NotBlank(message = "이메일을 입력해주세요.")
        @Pattern(
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "이메일 형식에 알맞지 않습니다."
        )
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,24}$",
                message = "비밀번호는 영문, 숫자, 특수문자 조합의 10자 이상 16자 이하여야 합니다."
        )
        String password,

        @NotBlank(message = "비밀번호 재확인을 입력해주세요.")
        String rePassword,

        @NotBlank(message = "전화번호를 입력해주세요.")
        @Pattern(
                regexp = "^01[016789]-?\\d{3,4}-?\\d{4}$",
                message = "010-xxxx-xxxx의 형식으로 입력해주세요."
        )
        String phone,
        String name,
        String nickname,
        String profileImage
){
}
