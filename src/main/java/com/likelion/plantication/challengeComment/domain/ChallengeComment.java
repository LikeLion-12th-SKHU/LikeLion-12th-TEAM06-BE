package com.likelion.plantication.challengeComment.domain;

import com.likelion.plantication.challenge.domain.Challenge;
import com.likelion.plantication.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CHALLENGE_COMMENT")
public class ChallengeComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHALLENGE_COMMENT_ID")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "CHALLENGE_ID", nullable = false)
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "CHALLENGE_COMMENT_CONTENT", nullable = false)
    private String content;

    @Column(name = "CHALLENGE_COMMENT_CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}