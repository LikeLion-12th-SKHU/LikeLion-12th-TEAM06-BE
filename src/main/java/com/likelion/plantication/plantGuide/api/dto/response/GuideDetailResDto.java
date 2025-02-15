package com.likelion.plantication.plantGuide.api.dto.response;

import com.likelion.plantication.plantGuide.domain.PlantGuide;
import lombok.Builder;
import org.joda.time.DateTime;

import java.util.Date;

@Builder
public record GuideDetailResDto(
        Long id,
        Long userId,
        String title,
        String sentence,
        String content,
        String image,
        Date createdAt,
        int view
) {
    public static GuideDetailResDto from(PlantGuide plantGuide) {
        return GuideDetailResDto.builder()
                .id(plantGuide.getId())
                .userId(plantGuide.getUser().getUserId())
                .title(plantGuide.getTitle())
                .sentence(plantGuide.getSentence())
                .content(plantGuide.getContent())
                .image(plantGuide.getImage())
                .createdAt(plantGuide.getCreatedAt())
                .view(plantGuide.getView())
                .build();
    }
}
