package com.likelion.plantication.diaryLike.api.dto.response;

import com.likelion.plantication.diaryLike.domain.DiaryLike;
import lombok.Builder;

import java.util.Date;

@Builder
public record DiaryLikeInfoResDto(
        Long id,
        Long diaryId,
        Long userId,
        Date createdAt
) {
    public static DiaryLikeInfoResDto from(DiaryLike diaryLike) {
        return DiaryLikeInfoResDto.builder()
                .id(diaryLike.getId())
                .diaryId(diaryLike.getDiary().getId())
                .userId(diaryLike.getUser().getUserId())
                .createdAt(diaryLike.getCreatedAt())
                .build();
    }
}
