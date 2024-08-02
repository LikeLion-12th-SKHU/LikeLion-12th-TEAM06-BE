package com.likelion.plantication.plantGuide.api.dto.response;

import com.likelion.plantication.plantGuide.domain.PlantGuide;
import lombok.Builder;
import org.joda.time.DateTime;

@Builder
public record GuideDetailResDto(
        Long id,
        Long userId,
        String title,
        String sentence,
        String content,
        String image,
        DateTime createdAt,
        int view
) {
    public static GuideDetailResDto from(PlantGuide plantGuide) {
        return GuideDetailResDto.builder()
                .id(plantGuide.getId())
                .userId(plantGuide.getUser().getId())
                .title(plantGuide.getTitle())
                .sentence(plantGuide.getSentence())
                .content(plantGuide.getContent())
                .image(plantGuide.getImage())
                .createdAt(plantGuide.getCreatedAt())
                .view(plantGuide.getView())
                .build();
    }
}
