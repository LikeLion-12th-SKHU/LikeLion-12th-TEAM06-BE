package com.likelion.plantication.challenge.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.likelion.plantication.challenge.domain.Challenge;
import com.likelion.plantication.challenge.domain.ChallengeStatus;
import com.likelion.plantication.user.domain.User;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
public record ChallengeResDto(
        Long challengeId,
        String title,
        String content,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime modifiedAt,

        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate start,
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate end,

        String nickname,
        ChallengeStatus status
) {
    public static ChallengeResDto of(Challenge challenge) {
        return ChallengeResDto.builder()
                .challengeId(challenge.getChallengeId())
                .title(challenge.getTitle())
                .content(challenge.getContent())
                .createdAt(challenge.getCreatedAt())
                .modifiedAt(challenge.getModifiedAt())
                .start(challenge.getStart())
                .end(challenge.getEnd())
                .nickname(challenge.getUser().getNickname())
                .status(challenge.getStatus())
                .build();
    }
}
