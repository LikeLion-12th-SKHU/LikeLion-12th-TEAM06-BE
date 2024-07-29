package com.likelion.plantication.user.application;

import com.likelion.plantication.global.exception.code.SuccessCode;
import com.likelion.plantication.global.jwt.TokenProvider;
import com.likelion.plantication.user.api.dto.request.UserReqDto;
import com.likelion.plantication.user.api.dto.response.UserResDto;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseEntity<UserResDto> signUp(UserReqDto userReqDto) {
        String passwordEncode = passwordEncoder.encode(userReqDto.password());
        UserReqDto userSignUp = new UserReqDto(
                userReqDto.email(),
                passwordEncode,
                userReqDto.phone(),
                userReqDto.name(),
                userReqDto.nickname(),
                userReqDto.profileImage(),
                userReqDto.role()
        );

        // 중복 로직 추후 추가

        Role role = Role.getRole(userReqDto.role());

        User user = userRepository.save(User.builder()
                .email(userReqDto.email())
                .password(userReqDto.password())
                .phone(userReqDto.phone())
                .name(userReqDto.name())
                .nickname(userReqDto.nickname())
                .profileImage(userReqDto.profileImage())
                .role(Role.valueOf(userReqDto.role()))
                .build()
        );

        String accessToken = tokenProvider.createAccessToken(user);

        String refreshToken = tokenProvider.createRefreshToken(user);

        UserRefreshToken userRefreshToken = new UserRefreshToken();
        userRefreshToken.setRefreshToken(refreshToken);
        userRefreshToken.setUser(user);

        userRefreshTokenRepository.save(userRefreshToken);

        UserResDto userResDto = UserResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                // 상태 코드 추가할지말지 고민중 ...
                .build();

        return ResponseEntity.status(SuccessCode.USER_CREATE_SUCCESS.getHttpStatusCode())
                .body(userResDto);
    }
}
