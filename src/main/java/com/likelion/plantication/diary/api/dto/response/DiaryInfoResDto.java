package com.likelion.plantication.diary.api.dto.response;

import com.likelion.plantication.diary.domain.Diary;
import lombok.Builder;
import org.joda.time.DateTime;

import java.util.Date;

@Builder
public record DiaryInfoResDto(
        Long id,
        String title,
        String content,
        String image,
        DateTime createdAt,
        Date modifiedAt

) {
    public static DiaryInfoResDto from(Diary diary) {
        return DiaryInfoResDto.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .image(diary.getImage())
                .createdAt(diary.getCreatedAt())
                .modifiedAt(diary.getModifiedAt())
                .build();
    }
}
