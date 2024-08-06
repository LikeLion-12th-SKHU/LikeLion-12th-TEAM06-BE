package com.likelion.plantication.group.api;

import com.likelion.plantication.challenge.api.dto.request.ChallengeCreateReqDto;
import com.likelion.plantication.challenge.api.dto.request.ChallengeUpdateResDto;
import com.likelion.plantication.challenge.api.dto.response.ChallengeResDto;
import com.likelion.plantication.challenge.application.ChallengeCreateService;
import com.likelion.plantication.challenge.application.ChallengeReadService;
import com.likelion.plantication.challenge.application.ChallengeUDService;
import com.likelion.plantication.global.exception.code.SuccessCode;
import com.likelion.plantication.group.api.dto.request.GroupCreateReqDto;
import com.likelion.plantication.group.api.dto.request.GroupUpdateReqDto;
import com.likelion.plantication.group.api.dto.response.GroupResDto;
import com.likelion.plantication.group.application.GroupCreateService;
import com.likelion.plantication.group.application.GroupReadService;
import com.likelion.plantication.group.application.GroupUDService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "그룹", description = "그룹을 담당하는 API")
@RequestMapping("/api/v1/cooperate")
public class GroupController {

    private final GroupCreateService groupCreateService;
    private final GroupReadService groupReadService;
    private final GroupUDService groupUDService;

    @PostMapping("/create")
    @Operation(
            summary = "그룹 생성",
            description = "그룹을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "그룹 생성 성공"),
                    @ApiResponse(responseCode = "403", description = "권한에 문제가 있습니다"),
                    @ApiResponse(responseCode = "500", description = "내부 서버 에러")
            }
    )
        public ResponseEntity<GroupResDto> groupCreate(
            @Valid @RequestBody GroupCreateReqDto reqDto, @RequestParam Long userId) {

        GroupResDto resDto = groupCreateService.createGroup(reqDto, userId).getBody();
        return ResponseEntity.status(SuccessCode.GROUP_CREATE_SUCCESS.getHttpStatus()).body(resDto);
    }

    @GetMapping("/read")
    @Operation(
            summary = "모든 그룹 조회",
            description = "모든 그룹을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "그룹 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "권한에 문제가 있습니다"),
                    @ApiResponse(responseCode = "500", description = "내부 서버 에러")
            }
    )
    public ResponseEntity<List<GroupResDto>> getAllGroups() {

        List<GroupResDto> data = groupReadService.getAllGroups().getBody();
        return ResponseEntity.status(SuccessCode.GROUP_GET_SUCCESS.getHttpStatus()).body(data);
    }


    @GetMapping("/read/{groupId}")
    @Operation(
            summary = "특정 그룹 조회",
            description = "특정 그룹을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "그룹 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "권한에 문제가 있습니다"),
                    @ApiResponse(responseCode = "404", description = "해당 그룹을 찾을 수 없습니다"),
                    @ApiResponse(responseCode = "500", description = "내부 서버 에러")
            }
    )
    public ResponseEntity<GroupResDto> getGroupById(@PathVariable Long groupId) {

        GroupResDto resDto = groupReadService.getGroupById(groupId).getBody();
        return ResponseEntity.status(SuccessCode.GROUP_GET_SUCCESS.getHttpStatus()).body(resDto);
    }

    @PatchMapping("/update/{groupId}")
    @Operation(
            summary = "그룹 수정",
            description = "그룹을 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "그룹 수정 성공"),
                    @ApiResponse(responseCode = "403", description = "권한에 문제가 있습니다"),
                    @ApiResponse(responseCode = "404", description = "해당 그룹을 찾을 수 없습니다"),
                    @ApiResponse(responseCode = "500", description = "내부 서버 에러")
            }
    )
    public ResponseEntity<GroupResDto> updateGroup(@PathVariable Long groupId, @RequestParam Long userId, @Valid @RequestBody GroupUpdateReqDto groupUpdateReqDto) {

        GroupResDto resDto = groupUDService.updateGroup(groupId, userId, groupUpdateReqDto).getBody();
        return ResponseEntity.status(SuccessCode.GROUP_UPDATE_SUCCESS.getHttpStatus()).body(resDto);
    }

    @DeleteMapping("/delete/{groupId}")
    @Operation(
            summary = "그룹 삭제",
            description = "그룹을 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "그룹 삭제 성공"),
                    @ApiResponse(responseCode = "403", description = "권한에 문제가 있습니다"),
                    @ApiResponse(responseCode = "404", description = "해당 그룹을 찾을 수 없습니다"),
                    @ApiResponse(responseCode = "500", description = "내부 서버 에러")
            }
    )
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId, @RequestParam Long userId) {

        groupUDService.deleteGroup(groupId, userId);
        return ResponseEntity.status(SuccessCode.GROUP_DELETE_SUCCESS.getHttpStatus()).build();
    }
}