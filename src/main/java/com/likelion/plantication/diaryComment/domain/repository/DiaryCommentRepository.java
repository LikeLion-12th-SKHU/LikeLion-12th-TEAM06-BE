package com.likelion.plantication.diaryComment.domain.repository;

import com.likelion.plantication.diaryComment.domain.DiaryComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryCommentRepository extends JpaRepository<DiaryComment, Long> {
    List<DiaryComment> findByDiaryId(Long diaryId);
}
