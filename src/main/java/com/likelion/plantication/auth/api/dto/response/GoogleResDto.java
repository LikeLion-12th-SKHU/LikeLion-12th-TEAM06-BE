package com.likelion.plantication.auth.api.dto.response;

import lombok.Builder;

@Builder
public record GoogleResDto (
        String accessToken
){
}
