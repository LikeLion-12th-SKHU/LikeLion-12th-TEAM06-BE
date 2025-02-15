package com.likelion.plantication.challengeGroup.domain.repository;


import com.likelion.plantication.challengeGroup.domain.ChallengeGroup;
import com.likelion.plantication.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeGroupRepository extends JpaRepository<ChallengeGroup, Long> {
}