package com.likelion.plantication.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum Role {
    USER("USER", "일반 사용자 권한"),
    ADMIN("ADMIN", "관리자 권한");

    private final String code;
    private final String displayName;

    public static Role getRole(String code) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid role code: " + code));
    }
}