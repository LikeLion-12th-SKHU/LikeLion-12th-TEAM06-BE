package com.likelion.plantication.diaryComment.domain.repository;

import com.likelion.plantication.diary.domain.Diary;
import com.likelion.plantication.diaryComment.domain.DiaryComment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Optional;

public interface DiaryCommentRepository extends JpaRepository<DiaryComment, Long> {
    Optional<List<DiaryComment>> findByUser_UserId(Long userId);
}
