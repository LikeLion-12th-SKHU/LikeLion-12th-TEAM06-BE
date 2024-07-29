package com.likelion.plantication.plantGuide.api.dto.response;

import com.likelion.plantication.diary.api.dto.response.DiaryInfoResDto;
import com.likelion.plantication.diary.api.dto.response.DiaryListResDto;
import lombok.Builder;

import java.util.List;

@Builder
public record GuideListResDto(
        List<GuideInfoResDto> guides
) {
    public static GuideListResDto from(List<GuideInfoResDto> guides) {
        return GuideListResDto.builder()
                .guides(guides)
                .build();
    }
}
