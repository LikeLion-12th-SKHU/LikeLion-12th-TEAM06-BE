package com.likelion.plantication.user.api;

import com.likelion.plantication.user.api.dto.request.UserReqDto;
import com.likelion.plantication.user.api.dto.response.UserResDto;
import com.likelion.plantication.user.application.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "회원가입 / 로그인", description = "사용자의 회원가입과 로그인을 담당하는 API")
@RequestMapping("/api/v1/auth")

public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    @Operation(
            summary = "회원가입",
            description = "사용자에게 정보를 입력받아 데이터베이스에 저장하고, accessToken과 refreshToken을 발급해서 회원가입을 진행, 가입 시 입력한 사용자 정보와 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "회원가입 성공"),
                    // 추후 더 추가
                    @ApiResponse(responseCode = "500", description = "내부 서버 에러")
            })
    public ResponseEntity<UserResDto> signUp(@RequestBody UserReqDto userReqDto) {
        UserResDto userResDto = userService.signUp(userReqDto).getBody();
        return ResponseEntity.status(201).body(userResDto);
    }
}
