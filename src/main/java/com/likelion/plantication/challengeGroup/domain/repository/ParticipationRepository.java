package com.likelion.plantication.challengeGroup.domain.repository;

import com.likelion.plantication.challengeGroup.domain.ChallengeGroup;
import com.likelion.plantication.challengeGroup.domain.Participation;
import com.likelion.plantication.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    Optional<Participation> findByChallengeGroup_ChallengeGroupIdAndUser_UserId(Long challengeGroupId, Long userId);

    boolean existsByChallengeGroupAndUser(ChallengeGroup challengeGroup, User user);
}
