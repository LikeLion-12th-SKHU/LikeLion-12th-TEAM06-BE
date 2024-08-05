package com.likelion.plantication.challenge.domain.repository;

import com.likelion.plantication.challenge.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
