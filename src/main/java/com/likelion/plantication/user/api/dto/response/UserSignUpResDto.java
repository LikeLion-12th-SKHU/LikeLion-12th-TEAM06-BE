package com.likelion.plantication.user.api.dto.response;

import lombok.Builder;

@Builder
public record UserSignUpResDto(
        String accessToken,
        String refreshToken
) {
}