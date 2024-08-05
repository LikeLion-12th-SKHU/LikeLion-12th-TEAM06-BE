package com.likelion.plantication.diaryComment.api.dto.response;

import com.likelion.plantication.diaryComment.domain.DiaryComment;
import lombok.Builder;
import org.joda.time.DateTime;

@Builder
public record DiaryCommentInfoResDto(
        Long id,
        Long diaryId,
        Long userId,
        String content,
        DateTime createdAt,
        DateTime modifiedAt
) {
    public static DiaryCommentInfoResDto from(DiaryComment diaryComment) {
        return DiaryCommentInfoResDto.builder()
                .id(diaryComment.getId())
                .diaryId(diaryComment.getDiary().getId())
                .userId(diaryComment.getUser().getUserId())
                .content(diaryComment.getContent())
                .createdAt(diaryComment.getCreatedAt())
                .modifiedAt(diaryComment.getModifiedAt())
                .build();
    }
}
