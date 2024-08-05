package com.likelion.plantication.diaryLike.domain.repository;

import com.likelion.plantication.diaryLike.domain.DiaryLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryLikeRepository extends JpaRepository<DiaryLike, Long> {
    Optional<DiaryLike> findByDiary_IdAndUser_UserId(Long diaryId, Long userId);

    Long countByDiaryId(Long diaryId);
    Optional<List<DiaryLike>> findByUser_UserId(Long userId);
}
