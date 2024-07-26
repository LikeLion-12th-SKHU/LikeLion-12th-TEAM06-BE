package com.likelion.plantication.diaryComment.api.dto.request;

public record DiaryCommentSaveReqDto(
        Long diaryId,
        Long userId,
        String content
) {
}
