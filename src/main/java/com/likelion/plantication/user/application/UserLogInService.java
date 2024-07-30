package com.likelion.plantication.user.application;

import com.likelion.plantication.global.exception.CustomException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.global.jwt.TokenProvider;
import com.likelion.plantication.global.jwt.dto.RefreshAccessTokenDto;
import com.likelion.plantication.user.api.dto.request.UserLogInReqDto;
import com.likelion.plantication.user.api.dto.response.UserLogInResDto;
import com.likelion.plantication.user.domain.User;
import com.likelion.plantication.user.domain.UserRefreshToken;
import com.likelion.plantication.user.domain.repository.UserRefreshTokenRepository;
import com.likelion.plantication.user.domain.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLogInService {

    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final TokenProvider tokenProvider;
    private final TokenRefreshService tokenRefreshService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<UserLogInResDto> logIn(UserLogInReqDto userLogInReqDto) {
        String email = userLogInReqDto.email();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND_EXCEPTION,
                        ErrorCode.EMAIL_NOT_FOUND_EXCEPTION.getMessage() + email));

        if (!passwordEncoder.matches(userLogInReqDto.password(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH_EXCEPTION,
                    ErrorCode.PASSWORD_NOT_MATCH_EXCEPTION.getMessage());
        }

        // renewRefreshToken
        String createRefreshToken = tokenProvider.createRefreshToken(user);

        // refreshTokenEntity
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUser_UserId(user.getUserId())
                .orElseGet(() -> {
                    UserRefreshToken logoutUserRefreshToken = new UserRefreshToken();
                    logoutUserRefreshToken.setUser(user);
                    return userRefreshTokenRepository.save(logoutUserRefreshToken);
                });

        userRefreshToken.setRefreshToken(createRefreshToken);
        userRefreshToken.setUser(user);

        userRefreshTokenRepository.save(userRefreshToken);

        RefreshAccessTokenDto refreshAccessTokenDto = tokenRefreshService.refreshAccessToken(createRefreshToken);

        // renewAccessToken
        String refreshAccessToken = refreshAccessTokenDto.refreshAccessToken();

        UserLogInResDto userLogInResDto = UserLogInResDto.builder()
                .accessToken(refreshAccessToken)
                .refreshToken(createRefreshToken)
                .build();

        return ResponseEntity.ok()
                .body(userLogInResDto);
    }
}
