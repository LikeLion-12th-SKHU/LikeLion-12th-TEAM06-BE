package com.likelion.plantication.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChallengeStatus {
    PENDING("PENDING", "진행예정"),
    PROGRESS("PROGRESS", "진행중"),
    COMPLETED("COMPLETED", "완료");

    private final String code;
    private final String displayName;
}