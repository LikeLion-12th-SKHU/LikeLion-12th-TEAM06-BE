package com.likelion.plantication.diaryComment.api.dto.response;

import com.likelion.plantication.diary.api.dto.response.DiaryInfoResDto;
import com.likelion.plantication.diary.api.dto.response.DiaryListResDto;
import lombok.Builder;

import java.util.List;

@Builder
public record DiaryCommentListResDto(
        List<DiaryCommentInfoResDto> comments
) {
    public static DiaryCommentListResDto from(List<DiaryCommentInfoResDto> comments) {
        return DiaryCommentListResDto.builder()
                .comments(comments)
                .build();
    }
}
