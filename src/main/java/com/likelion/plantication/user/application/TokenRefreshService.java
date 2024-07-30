package com.likelion.plantication.user.application;

import com.likelion.plantication.global.exception.CustomException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.global.jwt.TokenProvider;
import com.likelion.plantication.global.jwt.dto.RefreshAccessTokenDto;
import com.likelion.plantication.global.jwt.dto.RefreshTokenParseDto;
import com.likelion.plantication.user.domain.User;
import com.likelion.plantication.user.domain.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenRefreshService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public RefreshAccessTokenDto refreshAccessToken(String refreshToken) {

        if (!tokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN_EXCEPTION,
                    ErrorCode.INVALID_TOKEN_EXCEPTION.getMessage());
        }

        RefreshTokenParseDto refreshTokenParseDto = refreshTokenParsing(refreshToken);

        User user = userRepository.findById(refreshTokenParseDto.userId())
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_EXCEPTION,
                        ErrorCode.ID_NOT_FOUND_EXCEPTION.getMessage()));

        String refreshAccessToken;

        try {
            refreshAccessToken = tokenProvider.createAccessToken(user);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.CREATE_TOKEN_FAIL_ERROR,
                    ErrorCode.CREATE_TOKEN_FAIL_ERROR.getMessage());
        }

        return RefreshAccessTokenDto.builder()
                .refreshAccessToken(refreshAccessToken)
                .build();
    }

    private RefreshTokenParseDto refreshTokenParsing(String refreshToken) {
        Claims claims = tokenProvider.getClaimsFromToken(refreshToken);

        Long userId = Long.parseLong(claims.getSubject());

        return RefreshTokenParseDto.builder()
                .userId(userId)
                .build();
    }
}