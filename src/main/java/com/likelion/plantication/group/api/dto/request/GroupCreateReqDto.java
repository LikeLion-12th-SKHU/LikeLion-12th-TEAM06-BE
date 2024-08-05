package com.likelion.plantication.group.api.dto.request;

import com.likelion.plantication.group.domain.PlantGroup;
import com.likelion.plantication.user.domain.User;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record GroupCreateReqDto(
        @NotBlank(message = "그룹 명을 필수로 작성해주세요.")
        @Size(max = 50, message = "최대 50자까지 작성 가능합니다.")
        String title,

        @NotBlank(message = "공개 여부를 선택해주세요.")
        Boolean openStatus,

        String image,

        @NotBlank(message = "그룹 인원수를 설정해주세요.")
        @Max(value = 100, message = "최대 100명까지 설정할 수 있습니다.")
        Integer people,

        @NotBlank(message = "그룹 소개를 작성해주세요.")
        String content,

        @NotNull(message = "작성일은 필수 입력 항목입니다.")
        @PastOrPresent(message = "작성일은 과거 또는 현재 날짜여야 합니다.")
        LocalDateTime createdAt
) {
    public PlantGroup toEntity(User user) {
        return PlantGroup.builder()
                .title(this.title)
                .openStatus(this.openStatus)
                .image(this.image)
                .people(this.people)
                .content(this.content)
                .createdAt(this.createdAt)
                .user(user)
                .build();
    }
}