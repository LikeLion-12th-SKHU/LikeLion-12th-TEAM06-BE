package com.likelion.plantication.auth.api;

import com.likelion.plantication.auth.api.dto.response.GoogleResDto;
import com.likelion.plantication.auth.application.GoogleOAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "oauth 회원가입 / 로그인", description = "소셜 로그인을 사용하여 회원가입 / 로그인")
@RequestMapping("/api/v1/auth")
public class GoogleOAuthController {

    private final GoogleOAuthService googleOAuthService;

    @GetMapping("/code/google")
    @Operation(
            summary = "구글 소셜 회원가입 / 로그인",
            description = "구글 로그인 후 리다이렉션된 URI입니다. 인가 코드를 받아서 accessToken을 요청하고, 회원가입 / 로그인",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원가입/로그인 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "내부 서버 에러")
            }
    )
    public ResponseEntity<GoogleResDto> googleOAuth(@RequestParam(name = "code") String code) {
        GoogleResDto googleResDto = googleOAuthService.googleOAuth(googleOAuthService.getGoogleToken(code)).getBody();

        return ResponseEntity.ok(googleResDto);
    }
}
