package com.likelion.plantication.challengeComment.application;

import com.likelion.plantication.challenge.domain.Challenge;
import com.likelion.plantication.challenge.domain.repository.ChallengeRepository;
import com.likelion.plantication.challengeComment.domain.ChallengeComment;
import com.likelion.plantication.challengeComment.domain.repository.ChallengeCommentRepository;
import com.likelion.plantication.global.exception.CustomException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.user.domain.User;
import com.likelion.plantication.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeCommentService {

    private final ChallengeCommentRepository challengeCommentRepository;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    public void addComment(Long challengeId, Long userId, String content) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHALLENGE_NOT_FOUND_EXCEPTION, ErrorCode.CHALLENGE_NOT_FOUND_EXCEPTION.getMessage()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION, ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        ChallengeComment comment = new ChallengeComment();
        comment.setChallenge(challenge);
        comment.setUser(user);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        challengeCommentRepository.save(comment);
    }

    public List<ChallengeComment> getCommentsByChallenge(Long challengeId) {
        return challengeCommentRepository.findByChallenge_ChallengeId(challengeId);
    }

    public void deleteComment(Long commentId, Long userId) {
        ChallengeComment comment = challengeCommentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND_EXCEPTION, ErrorCode.COMMENT_NOT_FOUND_EXCEPTION.getMessage()));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.USER_NOT_AUTHORIZED, "댓글을 삭제할 권한이 없습니다.");
        }

        challengeCommentRepository.delete(comment);
    }
}