package com.likelion.plantication.challengeGroup.api;

import com.likelion.plantication.challengeGroup.api.dto.response.ParticipationResDto;
import com.likelion.plantication.challengeGroup.application.ChallengeGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "챌린지 그룹", description = "챌린지 그룹을 담당하는 API")
@RequestMapping("/api/v1/challenge/{challengeId}")
public class ChallengeGroupController {

    private final ChallengeGroupService challengeGroupService;

    @PostMapping("/join")
    @Operation(
            summary = "챌린지 그룹에 사용자 추가",
            description = "지정된 챌린지 그룹에 사용자를 추가합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 추가 성공"),
                    @ApiResponse(responseCode = "404", description = "챌린지 그룹 또는 사용자 없음"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
            }
    )
    public ResponseEntity<String> addUserToChallengeGroup(
            @RequestParam @NotNull Long challengeGroupId,
            @RequestParam @NotNull Long userId) {
        challengeGroupService.addUserToChallengeGroup(challengeGroupId, userId);
        return ResponseEntity.ok("사용자가 챌린지 그룹에 추가되었습니다.");
    }

    @PutMapping("/update-completion")
    @Operation(
            summary = "사용자 완료 상태 업데이트",
            description = "ADMIN 권한을 가진 사용자가 특정 사용자의 완료 상태를 업데이트합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "완료 상태 업데이트 성공"),
                    @ApiResponse(responseCode = "404", description = "챌린지 그룹, 사용자, 또는 ADMIN 없음"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
            }
    )
    public ResponseEntity<String> updateUserCompletion(
            @RequestParam @NotNull Long challengeGroupId,
            @RequestParam @NotNull Long userId,
            @RequestParam @NotNull boolean completed,
            @RequestParam @NotNull Long adminId) {
        challengeGroupService.updateUserCompleted(challengeGroupId, userId, completed, adminId);
        return ResponseEntity.ok("사용자의 완료 상태가 업데이트되었습니다.");
    }

    @GetMapping("/result")
    @Operation(
            summary = "사용자 참여 상태 조회",
            description = "특정 챌린지 그룹 내 사용자의 참여 상태와 완료 날짜를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "참여 상태 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "챌린지 그룹 또는 사용자 없음")
            }
    )
    public ResponseEntity<ParticipationResDto> getUserParticipationStatus(
            @RequestParam @NotNull Long challengeGroupId,
            @RequestParam @NotNull Long userId) {
        ParticipationResDto participationResDto = challengeGroupService.getUserParticipationStatus(challengeGroupId, userId);
        return ResponseEntity.ok(participationResDto);
    }
}