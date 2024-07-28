package com.likelion.plantication.user.api.dto.response;

import lombok.Builder;

@Builder
public record UserResDto(
        String accessToken,
        String refreshToken
) {
}