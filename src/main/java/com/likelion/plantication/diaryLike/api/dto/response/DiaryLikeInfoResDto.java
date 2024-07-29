package com.likelion.plantication.diaryLike.api.dto.response;

import com.likelion.plantication.diaryLike.domain.DiaryLike;
import lombok.Builder;

import java.util.Date;

@Builder
public record DiaryLikeInfoResDto(
        Long id,
        Date createdAt
) {
    public static DiaryLikeInfoResDto from(DiaryLike diaryLike) {
        return DiaryLikeInfoResDto.builder()
                .id(diaryLike.getId())
                .createdAt(diaryLike.getCreatedAt())
                .build();
    }
}
