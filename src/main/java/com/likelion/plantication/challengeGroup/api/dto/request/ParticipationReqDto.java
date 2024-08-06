package com.likelion.plantication.challengeGroup.api.dto.request;

import com.likelion.plantication.challenge.api.dto.response.ChallengeResDto;
import com.likelion.plantication.challenge.domain.Challenge;
import com.likelion.plantication.challengeGroup.domain.ChallengeGroup;
import com.likelion.plantication.challengeGroup.domain.Participation;
import com.likelion.plantication.user.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public record ParticipationReqDto (
        @NotNull LocalDateTime joinDate,
        @NotNull Participation.ParticipationRole participationRole,
        boolean completed,
        LocalDateTime completionDate
) {
    public Participation toEntity(User user, ChallengeGroup challengeGroup) {
        return Participation.builder()
                .challengeGroup(challengeGroup)
                .user(user)
                .completed(this.completed)
                .joinDate(this.joinDate)
                .completionDate(this.completionDate)
                .participationRole(this.participationRole)
                .build();
    }
}