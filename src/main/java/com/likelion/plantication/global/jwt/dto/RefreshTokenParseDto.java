package com.likelion.plantication.global.jwt.dto;

import lombok.Builder;

@Builder
public record RefreshTokenParseDto(
        Long userId
) {
}