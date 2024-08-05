package com.likelion.plantication.challengeGroup.domain;

import com.likelion.plantication.challenge.domain.Challenge;
import com.likelion.plantication.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor()
@Table(name = "CHALLENGE_GROUP")
public class ChallengeGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHALLENGE_GROUP_ID")
    private Long challengeGroupId;

    @OneToOne
    @JoinColumn(name = "CHALLENGE_ID", nullable = false, unique = true)
    private Challenge challenge;

    @ManyToMany(mappedBy = "challengeGroups")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "challengeGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participations = new ArrayList<>();

    public void addParticipation(Participation participation) {
        participations.add(participation);
        participation.setChallengeGroup(this);
    }

    @Builder
    public ChallengeGroup(Long challengeGroupId, Challenge challenge, List<User> users, List<Participation> participations) {
        this.challengeGroupId = challengeGroupId;
        this.challenge = challenge;
        this.users = users;
        this.participations = participations;
    }
}