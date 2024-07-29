package com.likelion.plantication.diary.api.dto.request;

public record DiarySaveReqDto(
        String title,
        String content,
        String image
) {
}
