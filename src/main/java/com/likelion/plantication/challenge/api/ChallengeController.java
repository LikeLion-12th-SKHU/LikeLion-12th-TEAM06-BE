package com.likelion.plantication.challenge.api;

import com.likelion.plantication.challenge.api.dto.request.ChallengeCreateReqDto;
import com.likelion.plantication.challenge.api.dto.request.ChallengeUpdateReqDto;
import com.likelion.plantication.challenge.api.dto.response.ChallengeListResDto;
import com.likelion.plantication.challenge.api.dto.response.ChallengeResDto;
import com.likelion.plantication.challenge.application.ChallengeCreateService;
import com.likelion.plantication.challenge.application.ChallengeReadService;
import com.likelion.plantication.challenge.application.ChallengeUDService;
import com.likelion.plantication.global.exception.code.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "챌린지", description = "챌린지를 담당하는 API")
@RequestMapping("/api/v1/challenge")
public class ChallengeController {

    private final ChallengeCreateService challengeCreateService;
    private final ChallengeReadService challengeReadService;
    private final ChallengeUDService challengeUDService;

    @PostMapping(name = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "챌린지 작성",
            description = "챌린지를 작성합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "챌린지 작성 성공"),
                    @ApiResponse(responseCode = "403", description = "권한에 문제가 있습니다"),
                    @ApiResponse(responseCode = "500", description = "내부 서버 에러")
            }
    )
    public ResponseEntity<ChallengeResDto> createDiary(
            @RequestPart("challengeCreateReqDto") ChallengeCreateReqDto challengeCreateReqDto,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam Long userId ) throws IOException {
        ChallengeResDto resDto = challengeCreateService.createChallenge(challengeCreateReqDto, profileImage, userId).getBody();
        return ResponseEntity.status(SuccessCode.CHALLENGE_CREATE_SUCCESS.getHttpStatus()).body(resDto);
    }

    @GetMapping("/read")
    @Operation(
            summary = "모든 챌린지 조회",
            description = "모든 챌린지를 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "챌린지 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "권한에 문제가 있습니다"),
                    @ApiResponse(responseCode = "500", description = "내부 서버 에러")
            }
    )
    public ResponseEntity<ChallengeListResDto> getAllChallenges() {
        List<ChallengeResDto> challengeResDtos = challengeReadService.getAllChallenges();

        ChallengeListResDto challengeListResDto = ChallengeListResDto.of(challengeResDtos);

        return ResponseEntity.ok(challengeListResDto);
    }

    @GetMapping("/read/{challengeId}")
    @Operation(
            summary = "특정 챌린지 조회",
            description = "특정 챌린지를 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "챌린지 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "권한에 문제가 있습니다"),
                    @ApiResponse(responseCode = "404", description = "해당 챌린지를 찾을 수 없습니다"),
                    @ApiResponse(responseCode = "500", description = "내부 서버 에러")
            }
    )
    public ResponseEntity<ChallengeResDto> getChallengeById(@PathVariable Long challengeId) {

        ChallengeResDto resDto = challengeReadService.getChallengeById(challengeId).getBody();
        return ResponseEntity.status(SuccessCode.CHALLENGE_GET_SUCCESS.getHttpStatus()).body(resDto);
    }

    @PatchMapping(name = "/update/{challengeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "챌린지 수정",
            description = "챌린지를 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "챌린지 수정 성공"),
                    @ApiResponse(responseCode = "403", description = "권한에 문제가 있습니다"),
                    @ApiResponse(responseCode = "404", description = "해당 챌린지를 찾을 수 없습니다"),
                    @ApiResponse(responseCode = "500", description = "내부 서버 에러")
            }
    )
    public ResponseEntity<ChallengeResDto> updateChallenge(
            @RequestParam Long challengeId,
            @RequestPart("challengeUpdateReqDto") ChallengeUpdateReqDto challengeUpdateReqDto,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam Long userId) throws IOException {

        ChallengeResDto resDto = challengeUDService.updateChallenge(challengeId, challengeUpdateReqDto, profileImage, userId).getBody();
        return ResponseEntity.status(SuccessCode.CHALLENGE_UPDATE_SUCCESS.getHttpStatus()).body(resDto);
    }

    @DeleteMapping("/delete/{challengeId}")
    @Operation(
            summary = "챌린지 삭제",
            description = "챌린지를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "챌린지 삭제 성공"),
                    @ApiResponse(responseCode = "403", description = "권한에 문제가 있습니다"),
                    @ApiResponse(responseCode = "404", description = "해당 챌린지를 찾을 수 없습니다"),
                    @ApiResponse(responseCode = "500", description = "내부 서버 에러")
            }
    )
    public ResponseEntity<Void> deleteDiary(@PathVariable Long challengeId, @RequestParam Long userId) {

        challengeUDService.deleteChallenge(challengeId, userId);
        return ResponseEntity.status(SuccessCode.CHALLENGE_DELETE_SUCCESS.getHttpStatus()).build();
    }
}