package com.likelion.plantication.diaryLike.domain.repository;

import com.likelion.plantication.diaryLike.domain.DiaryLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryLikeRepository extends JpaRepository<DiaryLike, Long> {
    Optional<DiaryLike> findByDiaryIdAndUserId(Long diaryId, Long userId);
    Long countByDiaryId(Long diaryId);
}
