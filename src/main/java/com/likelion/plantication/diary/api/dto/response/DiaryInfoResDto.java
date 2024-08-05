package com.likelion.plantication.diary.api.dto.response;

import com.likelion.plantication.diary.domain.Diary;
import lombok.Builder;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.util.Date;

@Builder
public record DiaryInfoResDto(
        Long id,
        Long userId,
        String title,
        String content,
        String image,
        Date createdAt,
        Date modifiedAt

) {
    public static DiaryInfoResDto from(Diary diary) {
        return DiaryInfoResDto.builder()
                .id(diary.getId())
                .userId(diary.getUser().getUserId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .image(diary.getImage())
                .createdAt(diary.getCreatedAt())
                .modifiedAt(diary.getModifiedAt())
                .build();
    }
}
