package com.likelion.plantication.user.domain;

import com.likelion.plantication.global.exception.CustomException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER")
public class User {
    // 정규 표현식 사용 유효성 검사하기
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    // 대소문자 하나, 숫자 하나, 특수문자는 선택으로 8자에서 24자 사이 가능하도록!
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,24}$";

    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9가-힣]*$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "EMAIL", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "PHONE", nullable = false)
    private String phone;

    @Column(name = "NAME", length = 20, nullable = false)
    private String name;

    @Column(name = "NICKNAME", length = 20, nullable = false)
    private String nickname;

    @Column(name = "USER_IMAGE")
    private String profileImage;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;


    @Column(name = "ROLE", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public User(Long userId, String email, String password, String phone, String name, String nickname, String profileImage, LocalDateTime createdAt, LocalDateTime modifiedAt, Role role) {
        // 유효성 검사 로직 추가
        validateEmail(email);
        validatePassword(password);
        validateNickname(nickname);

        this.userId = userId;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.role = role;
    }

    // 이메일, 비밀번호, 닉네임 유효성 검사 로직
    private void validateEmail(String email) {
        if (!email.matches(EMAIL_REGEX)) {
            throw new CustomException(ErrorCode.INVALID_EMAIL_EXCEPTION, ErrorCode.INVALID_EMAIL_EXCEPTION.getMessage());
        }
    }

    private void validatePassword(String password) {
        if (!password.matches(PASSWORD_REGEX)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD_EXCEPTION, ErrorCode.INVALID_PASSWORD_EXCEPTION.getMessage());
        }
    }

    private void validateNickname(String nickname) {
        if (!NICKNAME_PATTERN.matcher(nickname).matches()) {
            throw new CustomException(ErrorCode.INVALID_NICKNAME_EXCEPTION, ErrorCode.INVALID_NICKNAME_EXCEPTION.getMessage());
        }
    }
}
