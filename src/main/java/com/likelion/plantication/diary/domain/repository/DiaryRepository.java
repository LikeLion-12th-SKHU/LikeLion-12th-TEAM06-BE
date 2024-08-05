package com.likelion.plantication.diary.domain.repository;

import com.likelion.plantication.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<List<Diary>> findByUser_UserId(Long userId);
}
