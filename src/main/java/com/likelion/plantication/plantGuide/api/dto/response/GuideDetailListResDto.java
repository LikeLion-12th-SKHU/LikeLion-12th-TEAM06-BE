package com.likelion.plantication.plantGuide.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GuideDetailListResDto(
        List<GuideDetailResDto> guides
) {
    public static GuideDetailListResDto from(List<GuideDetailResDto> guides) {
        return GuideDetailListResDto.builder()
                .guides(guides)
                .build();
    }
}
