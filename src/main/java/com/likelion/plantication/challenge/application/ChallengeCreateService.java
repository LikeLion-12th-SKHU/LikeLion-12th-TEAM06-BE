package com.likelion.plantication.challenge.application;

import com.likelion.plantication.challenge.api.dto.request.ChallengeCreateReqDto;
import com.likelion.plantication.challenge.api.dto.response.ChallengeResDto;
import com.likelion.plantication.challenge.domain.Challenge;
import com.likelion.plantication.challenge.domain.repository.ChallengeRepository;
import com.likelion.plantication.challengeGroup.domain.ChallengeGroup;
import com.likelion.plantication.challengeGroup.domain.Participation;
import com.likelion.plantication.challengeGroup.domain.repository.ChallengeGroupRepository;
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
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeCreateService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final ChallengeGroupRepository challengeGroupRepository;

    private final S3Service s3Service;

    public ResponseEntity<ChallengeResDto> createChallenge (
            ChallengeCreateReqDto challengeCreateReqDto, MultipartFile multipartFile, Long userId ) throws IOException {

        String image = s3Service.upload(multipartFile, "challenge");

        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION, ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        Challenge challenge = Challenge.builder()
                .title(challengeCreateReqDto.title())
                .content(challengeCreateReqDto.content())
                .image(image)
                .start(challengeCreateReqDto.start())
                .end(challengeCreateReqDto.end())
                .build();
        challengeRepository.save(challenge);

        // 동시에 챌린지 그룹도 생성
        ChallengeGroup challengeGroup = new ChallengeGroup();
        challengeGroup.setChallenge(challenge);
        challengeGroupRepository.save(challengeGroup);

        // 챌린지 작성자를 ADMIN으로 ChallengeGroup에 추가
        Participation participation = new Participation();
        participation.setUser(user);
        participation.setChallengeGroup(challengeGroup);
        participation.setJoinDate(LocalDateTime.now());
        participation.setParticipationRole(Participation.ParticipationRole.ADMIN);

        challengeGroup.addParticipation(participation);
        challengeGroupRepository.save(challengeGroup);

        ChallengeResDto challengeResDto = ChallengeResDto.of(challenge);

        return ResponseEntity.status(SuccessCode.CHALLENGE_CREATE_SUCCESS.getHttpStatus())
                .body(challengeResDto);
    }
}
