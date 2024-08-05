package com.likelion.plantication.challengeGroup.api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
public record ParticipationResDto (
        boolean completed,
        LocalDateTime completionDate
) {
}
