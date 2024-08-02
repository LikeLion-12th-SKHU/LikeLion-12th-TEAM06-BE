package com.likelion.plantication.diaryComment.api.dto.request;

import org.joda.time.DateTime;

public record DiaryCommentUpdateReqDto(
        Long id,
        Long diaryId,
        Long userId,
        String content,
        DateTime createdAt
) {
}
