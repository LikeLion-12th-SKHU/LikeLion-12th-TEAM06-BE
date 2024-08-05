package com.likelion.plantication.challenge.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ChallengeUpdateReqDto(
        @NotBlank(message = "챌린지 명을 필수로 작성해주세요.")
        @Size(max = 50, message = "최대 50자까지 작성 가능합니다.")
                String title,

        @NotBlank(message = "챌린지 내용을 필수로 작성해주세요.")
        @Size(max = 4000, message = "최대 4000자까지 작성 가능합니다.")
        String content,

        @NotBlank(message = "챌린지 시작일을 필수로 작성해주세요.")
        LocalDate start,

        @NotBlank(message = "챌린지 종료일을 필수로 작성해주세요.")
        LocalDate end,

        @NotNull(message = "작성일은 필수 입력 항목입니다.")
        @PastOrPresent(message = "작성일은 과거 또는 현재 날짜여야 합니다.")
        LocalDateTime createdAt
) {
}
