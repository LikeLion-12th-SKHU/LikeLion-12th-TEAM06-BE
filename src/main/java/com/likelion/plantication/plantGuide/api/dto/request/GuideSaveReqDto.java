package com.likelion.plantication.plantGuide.api.dto.request;

public record GuideSaveReqDto(
        Long userId,
        String title,
        String sentence,
        String content,
        String image
) {
}
