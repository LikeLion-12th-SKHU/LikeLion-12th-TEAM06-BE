package com.likelion.plantication.plantGuide.api.dto.response;

import com.likelion.plantication.diary.api.dto.response.DiaryInfoResDto;
import com.likelion.plantication.diary.domain.Diary;
import com.likelion.plantication.plantGuide.domain.PlantGuide;
import lombok.Builder;
import org.joda.time.DateTime;

import java.util.Date;

@Builder
public record GuideInfoResDto(
        Long id,
        Long userId,
        String title,
        String sentence,
        String image,
        Date createdAt,
        int view
) {
    public static GuideInfoResDto from(PlantGuide plantGuide) {
        return GuideInfoResDto.builder()
                .id(plantGuide.getId())
                .userId(plantGuide.getUser().getUserId())
                .title(plantGuide.getTitle())
                .sentence(plantGuide.getSentence())
                .image(plantGuide.getImage())
                .createdAt(plantGuide.getCreatedAt())
                .view(plantGuide.getView())
                .build();
    }
}
