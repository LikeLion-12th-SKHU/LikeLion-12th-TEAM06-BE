package com.likelion.plantication.challenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.plantication.challenge.api.dto.request.ChallengeUpdateReqDto;
import com.likelion.plantication.challengeGroup.domain.ChallengeGroup;
import com.likelion.plantication.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CHALLENGE")
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHALLENGE_ID")
    private Long challengeId;

    @Column(name = "CHALLENGE_TITLE", length = 50, nullable = false)
    private String title;

    @Column(name = "CHALLENGE_CONTENT", length = 4000, nullable = false)
    private String content;

    @Column(name = "CHALLENGE_IMAGE")
    private String image;

    @Column(name = "CHALLENGE_CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "CHALLENGE_MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "CHALLENGE_START", nullable = false)
    private LocalDate start;

    @Column(name = "CHALLENGE_END", nullable = false)
    private LocalDate end;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "CHALLENGE_STATUS", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    @OneToOne(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private ChallengeGroup challengeGroup;

    // hashtag

    @PreUpdate
    protected void updateStatus() {
        LocalDate now = LocalDate.now();
        if (now.isBefore(start)) {
            this.status = ChallengeStatus.PENDING;
        } else if (now.isAfter(end)) {
            this.status = ChallengeStatus.COMPLETED;
        } else {
            this.status = ChallengeStatus.PROGRESS;
        }
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        updateStatus();
    }

    @Builder
    public Challenge(String title, String content, String image, LocalDateTime createdAt, LocalDate start, LocalDate end, User user, ChallengeStatus status) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.createdAt = createdAt;
        this.start = start;
        this.end = end;
        this.user = user;
        this.status = status;
    }

    public void update(ChallengeUpdateReqDto challengeUpdateReqDto) {
        this.title = challengeUpdateReqDto.title();
        this.content = challengeUpdateReqDto.content();
        this.start = challengeUpdateReqDto.start();
        this.end = challengeUpdateReqDto.end();
    }

    public void updateImage(String image) {
        this.image = image;
    }
}
