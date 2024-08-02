package com.likelion.plantication.challenge.application;

import com.likelion.plantication.challenge.api.dto.request.ChallengeCreateReqDto;
import com.likelion.plantication.challenge.api.dto.response.ChallengeResDto;
import com.likelion.plantication.challenge.domain.Challenge;
import com.likelion.plantication.challenge.domain.ChallengeStatus;
import com.likelion.plantication.challenge.domain.repository.ChallengeRepository;
import com.likelion.plantication.global.exception.CustomException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.global.exception.code.SuccessCode;
import com.likelion.plantication.user.domain.User;
import com.likelion.plantication.user.domain.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeCreateService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    public ResponseEntity<ChallengeResDto> createChallenge (
            ChallengeCreateReqDto challengeCreateReqDto, Long userId ) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION, ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        Challenge challenge = challengeCreateReqDto.toEntity(user);
        challengeRepository.save(challenge);

        ChallengeResDto challengeResDto = ChallengeResDto.of(challenge);


        // 여기 body 이상...
        return ResponseEntity.status(SuccessCode.CHALLENGE_CREATE_SUCCESS.getHttpStatus())
                .body(challengeResDto);
    }
}
