package com.likelion.plantication.challengeGroup.application;

import com.likelion.plantication.challenge.domain.Challenge;
import com.likelion.plantication.challengeGroup.api.dto.request.ParticipationReqDto;
import com.likelion.plantication.challengeGroup.api.dto.response.ParticipationResDto;
import com.likelion.plantication.challengeGroup.domain.ChallengeGroup;
import com.likelion.plantication.challengeGroup.domain.Participation;
import com.likelion.plantication.challengeGroup.domain.repository.ChallengeGroupRepository;
import com.likelion.plantication.challengeGroup.domain.repository.ParticipationRepository;
import com.likelion.plantication.global.exception.CustomException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.user.domain.User;
import com.likelion.plantication.user.domain.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeGroupService {

    private final ChallengeGroupRepository challengeGroupRepository;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;

    @Transactional
    public void addUserToChallengeGroup(Long challengeGroupId, Long userId, ParticipationReqDto participationReqDto) {
        // 사용자 및 챌린지 그룹 조회
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION, ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        ChallengeGroup challengeGroup = challengeGroupRepository.findById(challengeGroupId).orElseThrow(
                () -> new CustomException(ErrorCode.CHALLENGE_GROUP_NOT_FOUND_EXCEPTION, ErrorCode.CHALLENGE_GROUP_NOT_FOUND_EXCEPTION.getMessage()));

        // 이미 참여 중인지 확인
        boolean alreadyParticipating = participationRepository.existsByChallengeGroupAndUser(challengeGroup, user);
        if (alreadyParticipating) {
            throw new CustomException(ErrorCode.PARTICIPATION_ALREADY_EXISTS, "사용자는 이미 이 챌린지 그룹에 참여하고 있습니다.");
        }

        Participation participation = participationReqDto.toEntity(user, challengeGroup);

        challengeGroup.addParticipation(participation);

        challengeGroupRepository.save(challengeGroup);
    }

    @Transactional
    public void updateUserCompleted(Long challengeGroupId, Long userId, boolean completed, Long adminId) {
        User admin = userRepository.findById(adminId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION, ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        if (!adminCheck(challengeGroupId, admin)) {
            throw new CustomException(ErrorCode.USER_NOT_AUTHORIZED, "ADMIN이 아닙니다.");
        }

        Participation participation = participationRepository.findByChallengeGroup_ChallengeGroupIdAndUser_UserId(
                challengeGroupId, userId).orElseThrow(
                () -> new CustomException(ErrorCode.CHALLENGE_GROUP_STATUS_NOT_FOUND_EXCEPTION, ErrorCode.CHALLENGE_GROUP_NOT_FOUND_EXCEPTION.getMessage()));

        participation.setCompleted(completed);
        participation.setCompletionDate(completed ? LocalDateTime.now() : null);
        participationRepository.save(participation);
    }

    private boolean adminCheck(Long challengeGroupId, User admin) {
        return participationRepository.findByChallengeGroup_ChallengeGroupIdAndUser_UserId(challengeGroupId, admin.getUserId())
                .map(Participation::getParticipationRole)
                .stream().anyMatch(role -> role == Participation.ParticipationRole.ADMIN);
    }

    public ParticipationResDto getUserParticipationStatus(Long challengeGroupId, Long userId) {
        Participation participation = participationRepository.findByChallengeGroup_ChallengeGroupIdAndUser_UserId(challengeGroupId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PARTICIPATION_NOT_FOUND_EXCEPTION, ErrorCode.PARTICIPATION_NOT_FOUND_EXCEPTION.getMessage()));

        return ParticipationResDto.of(participation);
    }
}
