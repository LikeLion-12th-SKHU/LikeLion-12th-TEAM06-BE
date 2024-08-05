package com.likelion.plantication.group.api.dto.response;

import com.likelion.plantication.group.domain.PlantGroup;
import com.likelion.plantication.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GroupResDto (
        Long groupId,
        String title,
        Boolean openStatus,
        String image,
        Integer people,
        String content,
        LocalDateTime createAt,
        User user
) {

    public static GroupResDto of(PlantGroup groups) {
        return GroupResDto.builder()
                .groupId(groups.getGroupId())
                .title(groups.getTitle())
                .openStatus(groups.getOpenStatus())
                .image(groups.getImage())
                .people(groups.getPeople())
                .content(groups.getContent())
                .createAt(groups.getCreatedAt())
                .user(groups.getUser())
                .build();
    }
}
