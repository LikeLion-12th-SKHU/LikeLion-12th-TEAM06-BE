package com.likelion.plantication.plantGuide.api.dto.request;

public record GuideSaveReqDto(
        String title,
        String sentence,
        String content
) {
}
