package com.likelion.plantication.challengeGroup.api.dto.response;

import com.likelion.plantication.challenge.api.dto.response.ChallengeResDto;
import com.likelion.plantication.challenge.domain.Challenge;
import com.likelion.plantication.challengeGroup.domain.ChallengeGroup;
import com.likelion.plantication.challengeGroup.domain.Participation;
import com.likelion.plantication.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
public record ParticipationResDto (
        Long participationId,
        String challengeGroup,
        String user,

        boolean completed,
        LocalDateTime completionDate
) {
    public static ParticipationResDto of(Participation participation) {
        return ParticipationResDto.builder()
                .participationId(participation.getParticipationId())
                .challengeGroup(participation.getChallengeGroup().getChallenge().getTitle())
                .user(participation.getUser().getNickname())
                .completed(participation.isCompleted())
                .completionDate(participation.getCompletionDate())
                .build();
    }
}
