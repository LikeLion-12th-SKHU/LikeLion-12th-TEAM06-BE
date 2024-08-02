package com.likelion.plantication.user.api;

import com.likelion.plantication.auth.application.GoogleOAuthService;
import com.likelion.plantication.user.api.dto.request.UserLogInReqDto;
import com.likelion.plantication.user.api.dto.request.UserSignUpReqDto;
import com.likelion.plantication.user.api.dto.response.UserLogInResDto;
import com.likelion.plantication.user.api.dto.response.UserSignUpResDto;
import com.likelion.plantication.user.application.UserLogInService;
import com.likelion.plantication.user.application.UserSignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "회원가입 / 로그인", description = "사용자의 회원가입과 로그인을 담당하는 API")
@RequestMapping("/api/v1/auth")

public class UserController {

    private final UserSignUpService userSignUpService;
    private final UserLogInService userLogInService;
    private final GoogleOAuthService googleOAuthService;

    @PostMapping("/signup")
    @Operation(
            summary = "회원가입",
            description = "사용자에게 정보를 입력받아 데이터베이스에 저장하고, accessToken과 refreshToken을 발급해서 회원가입을 진행, 가입 시 입력한 사용자 정보와 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "회원가입 성공"),
                    // 추후 더 추가
                    @ApiResponse(responseCode = "500", description = "내부 서버 에러")
            })
    public ResponseEntity<UserSignUpResDto> signUp(@RequestBody UserSignUpReqDto userSignUpReqDto) {
        UserSignUpResDto userSignUpResDto = userSignUpService.signUp(userSignUpReqDto).getBody();
        return ResponseEntity.status(201).body(userSignUpResDto);
    }

    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = "사용자의 이메일, 비밀번호를 입력받습니다. 유저의 비밀번호와 일치하는지 검사하고, 갱신된 refreshToken, accessToken을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 비밀번호 입력"),
                    @ApiResponse(responseCode = "404", description = "이메일을 찾을수 없거나, 잘못된 값을 입력"),
                    @ApiResponse(responseCode = "500", description = "내부 서버 에러")
            }
    )
    public ResponseEntity<UserLogInResDto> login(@RequestBody UserLogInReqDto userLogInReqDto) {
        UserLogInResDto userLogInResDto = userLogInService.logIn(userLogInReqDto).getBody();
        return ResponseEntity.status(200).body(userLogInResDto);
    }
}
