package com.likelion.plantication.diary.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record DiaryListResDto(
        List<DiaryInfoResDto> diaries
) {
    public static DiaryListResDto from(List<DiaryInfoResDto> diaries) {
        return DiaryListResDto.builder()
                .diaries(diaries)
                .build();
    }
}