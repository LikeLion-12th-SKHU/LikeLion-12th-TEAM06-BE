package com.likelion.plantication.myPage.domain.repository;

import com.likelion.plantication.diary.domain.Diary;
import com.likelion.plantication.myPage.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    Optional<List<Inquiry>> findByUser_UserId(Long userId);
}
