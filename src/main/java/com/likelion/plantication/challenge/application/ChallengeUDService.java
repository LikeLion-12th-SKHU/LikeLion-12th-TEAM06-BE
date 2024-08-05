package com.likelion.plantication.challenge.application;

import com.likelion.plantication.challenge.api.dto.request.ChallengeUpdateResDto;
import com.likelion.plantication.challenge.api.dto.response.ChallengeResDto;
import com.likelion.plantication.challenge.domain.Challenge;
import com.likelion.plantication.challenge.domain.repository.ChallengeRepository;
import com.likelion.plantication.global.exception.CustomException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.global.exception.code.SuccessCode;
import com.likelion.plantication.global.service.S3Service;
import com.likelion.plantication.user.domain.User;
import com.likelion.plantication.user.domain.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeUDService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    private final S3Service s3Service;

    public ResponseEntity<ChallengeResDto> updateChallenge(Long challengeId, ChallengeUpdateResDto challengeUpdateResDto, MultipartFile image, Long userId) throws IOException {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION, ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_NOT_FOUND_EXCEPTION, ErrorCode.CHALLENGE_NOT_FOUND_EXCEPTION.getMessage()));

        // 작성자와 로그인한 유저가 같은지 확인
        if (!challenge.getUser().equals(user)) {
            throw new CustomException(ErrorCode.USER_NOT_AUTHORIZED, ErrorCode.USER_NOT_AUTHORIZED.getMessage());
        }

        // 이미지가 있을 경우 이미지도 수정
        if (image != null && !image.isEmpty()) {
            String updateImage = s3Service.upload(image, "challenge");
            challenge.updateImage(updateImage);
        }

        // 챌린지 업데이트
        challenge.update(
                challengeUpdateResDto.title(),
                challengeUpdateResDto.content(),
                challengeUpdateResDto.image(),
                challengeUpdateResDto.createdAt(),
                challengeUpdateResDto.start(),
                challengeUpdateResDto.end());
        challengeRepository.save(challenge);

        // 성공 응답
        ChallengeResDto challengeResDto = ChallengeResDto.of(challenge);
        return ResponseEntity.status(SuccessCode.CHALLENGE_UPDATE_SUCCESS.getHttpStatus()).body(challengeResDto);
    }

    @Transactional
    public ResponseEntity<Void> deleteChallenge(Long challengeId, Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION, ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_NOT_FOUND_EXCEPTION, ErrorCode.CHALLENGE_NOT_FOUND_EXCEPTION.getMessage()));

        // 작성자와 로그인한 유저가 같은지 확인
        if (!challenge.getUser().equals(user)) {
            throw new CustomException(ErrorCode.USER_NOT_AUTHORIZED, ErrorCode.USER_NOT_AUTHORIZED.getMessage());
        }

        // 챌린지 삭제
        challengeRepository.delete(challenge);

        // 성공 응답 반환
        return ResponseEntity.status(SuccessCode.CHALLENGE_UPDATE_SUCCESS.getHttpStatus()).build();
    }
}
