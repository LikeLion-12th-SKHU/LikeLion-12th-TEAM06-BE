package com.likelion.plantication.challenge.application;

import com.likelion.plantication.challenge.api.dto.response.ChallengeListResDto;
import com.likelion.plantication.challenge.api.dto.response.ChallengeResDto;
import com.likelion.plantication.challenge.domain.Challenge;
import com.likelion.plantication.challenge.domain.repository.ChallengeRepository;
import com.likelion.plantication.global.exception.CustomException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.global.exception.code.SuccessCode;
import com.likelion.plantication.user.domain.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeReadService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    public List<ChallengeResDto> getAllChallenges() {
        List<Challenge> challenges = challengeRepository.findAll();

        // 엔티티를 DTO로 변환
        return challenges.stream()
                .map(ChallengeResDto::of)
                .collect(Collectors.toList());
    }

    public ResponseEntity<ChallengeResDto> getChallengeById(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_NOT_FOUND_EXCEPTION, ErrorCode.CHALLENGE_NOT_FOUND_EXCEPTION.getMessage()));

        // 성공 응답
        ChallengeResDto challengeResDto = ChallengeResDto.of(challenge);
        return ResponseEntity.status(SuccessCode.CHALLENGE_GET_SUCCESS.getHttpStatus()).body(challengeResDto);
    }
}
