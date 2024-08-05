package com.likelion.plantication.user.application;

import com.likelion.plantication.global.exception.CustomException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.global.exception.code.SuccessCode;
import com.likelion.plantication.global.jwt.TokenProvider;
import com.likelion.plantication.global.service.S3Service;
import com.likelion.plantication.user.api.dto.request.UserSignUpReqDto;
import com.likelion.plantication.user.api.dto.response.UserSignUpResDto;
import com.likelion.plantication.user.domain.Role;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignUpService {

    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    private final S3Service s3Service;

    @Transactional
    public ResponseEntity<UserSignUpResDto> signUp(UserSignUpReqDto userSignUpReqDto, MultipartFile profileImage) throws IOException {

        if (!userSignUpReqDto.password().equals(userSignUpReqDto.rePassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH_EXCEPTION, "비밀번호가 일치하지 않습니다.");
        }

        String passwordEncode = passwordEncoder.encode(userSignUpReqDto.password());

        String profileImageUrl = null;
        if (profileImage != null && !profileImage.isEmpty()) {
            profileImageUrl = s3Service.upload(profileImage, "profile-images");
        }

        User user = userRepository.save(User.builder()
                .email(userSignUpReqDto.email())
                .password(passwordEncode)
                .phone(userSignUpReqDto.phone())
                .name(userSignUpReqDto.name())
                .nickname(userSignUpReqDto.nickname())
                .profileImage(profileImageUrl)
                .role(Role.USER)
                .build()
        );

        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken(user);

        UserRefreshToken userRefreshToken = new UserRefreshToken();
        userRefreshToken.setRefreshToken(refreshToken);
        userRefreshToken.setUser(user);

        userRefreshTokenRepository.deleteByUser(user);
        userRefreshTokenRepository.save(userRefreshToken);

        UserSignUpResDto userSignUpResDto = UserSignUpResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return ResponseEntity.status(SuccessCode.USER_CREATE_SUCCESS.getHttpStatusCode())
                .body(userSignUpResDto);
    }
}
