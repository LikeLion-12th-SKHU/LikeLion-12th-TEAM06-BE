package com.likelion.plantication.challengeGroup.application;

import com.likelion.plantication.challenge.domain.Challenge;
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
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeGroupService {

    private final ChallengeGroupRepository challengeGroupRepository;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;

    public void addUserToChallengeGroup(Long challengeGroupId, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION, ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        ChallengeGroup challengeGroup = challengeGroupRepository.findById(challengeGroupId).orElseThrow(
                () -> new CustomException(ErrorCode.CHALLENGE_GROUP_NOT_FOUND_EXCEPTION, ErrorCode.CHALLENGE_GROUP_NOT_FOUND_EXCEPTION.getMessage()));

        // 참여자 추가
        Participation participation = new Participation();
        participation.setUser(user);
        participation.setChallengeGroup(challengeGroup);
        participation.setJoinDate(LocalDateTime.now());
        participation.setParticipationRole(Participation.ParticipationRole.USER);

        // 챌린지 그룹에 참여자 추가
        challengeGroup.addParticipation(participation);

        challengeGroupRepository.save(challengeGroup);
    }

    public void updateUserCompleted(Long challengeGroupId, Long userId, boolean completed, Long adminId) {

        // ADMIN 확인
        User admin = userRepository.findById(adminId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION, ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        if (!adminCheck(challengeGroupId, adminId)) {
            throw new IllegalArgumentException("ADMIN이 아닙니다.");
        }

        // 사용자의 참여 상태 조회
        Participation participation = participationRepository.findByChallengeGroup_ChallengeGroupIdAndUser_UserId(challengeGroupId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_GROUP_STATUS_NOT_FOUND_EXCEPTION, ErrorCode.CHALLENGE_GROUP_STATUS_NOT_FOUND_EXCEPTION.getMessage()));

        participation.setCompleted(completed);
        participation.setCompletionDate(completed ? LocalDateTime.now() : null);
        participationRepository.save(participation);
    }

    private boolean adminCheck(Long challengeGroupId, Long adminId) {
        return participationRepository.findByChallengeGroup_ChallengeGroupIdAndUser_UserId(challengeGroupId, adminId)
                .map(Participation::getParticipationRole)
                .filter(role -> role == Participation.ParticipationRole.ADMIN)
                .isPresent();
    }

    public ParticipationResDto getUserParticipationStatus(Long challengeGroupId, Long userId) {
        Participation participation = participationRepository.findByChallengeGroup_ChallengeGroupIdAndUser_UserId(challengeGroupId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PARTICIPATION_NOT_FOUND_EXCEPTION, ErrorCode.PARTICIPATION_NOT_FOUND_EXCEPTION.getMessage()));

        return new ParticipationResDto(participation.isCompleted(), participation.getCompletionDate());
    }
}
