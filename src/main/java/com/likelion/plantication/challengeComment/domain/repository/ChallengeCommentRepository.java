package com.likelion.plantication.challengeComment.domain.repository;

import com.likelion.plantication.challengeComment.domain.ChallengeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeCommentRepository extends JpaRepository<ChallengeComment, Long> {
    List<ChallengeComment> findByChallenge_ChallengeId(Long challengeId);
    List<ChallengeComment> findByUser_UserId(Long userId);
    List<ChallengeComment> findByChallenge_ChallengeIdAndUser_UserId(Long challengeId, Long userId);
}
