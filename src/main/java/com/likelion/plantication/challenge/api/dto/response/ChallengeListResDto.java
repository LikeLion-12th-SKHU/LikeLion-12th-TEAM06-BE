package com.likelion.plantication.challenge.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ChallengeListResDto(
        List<ChallengeResDto> challenges
) {
    public static ChallengeListResDto of(List<ChallengeResDto> challenges) {
        return ChallengeListResDto.builder()
                .challenges(challenges)
                .build();
    }
}