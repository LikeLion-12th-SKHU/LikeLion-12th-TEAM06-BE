package com.likelion.plantication.challengeGroup.domain;

import com.likelion.plantication.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARTICIPATION_ID")
    private Long participationId;

    @ManyToOne
    @JoinColumn(name = "CHALLENGE_GROUP_ID")
    private ChallengeGroup challengeGroup;

    @ManyToOne
    @JoinColumn(name = "PARTICIPATION_USER_ID")
    private User user;

    @Column(name = "PARTICIPATION_JOIN_DATE")
    private LocalDateTime joinDate;

    @Column(name = "PARTICIPATION_COMPLETION_DATE")
    private LocalDateTime completionDate;

    @Column(name = "PARTICIPATION_COMPLETED")
    private boolean completed;

    @Enumerated(EnumType.STRING)
    @Column(name = "PARTICIPATION_ROLE")
    private ParticipationRole participationRole;

    @PrePersist
    protected void onCreate() {
        this.completed = false;
    }

    // Enum for user roles
    public enum ParticipationRole {
        ADMIN,
        USER
    }
}