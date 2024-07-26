package com.likelion.plantication.diaryLike.application;

import com.likelion.plantication.diary.domain.Diary;
import com.likelion.plantication.diary.domain.repository.DiaryRepository;
import com.likelion.plantication.diaryLike.api.dto.request.DiaryLikeSaveReqDto;
import com.likelion.plantication.diaryLike.api.dto.response.DiaryLikeInfoResDto;
import com.likelion.plantication.diaryLike.domain.DiaryLike;
import com.likelion.plantication.diaryLike.domain.repository.DiaryLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryLikeService {
    private final DiaryLikeRepository diaryLikeRepository;
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    // 좋아요 생성
    public DiaryLikeInfoResDto addLike(DiaryLikeSaveReqDto diaryLikeSaveReqDto) {
        Diary diary = diaryRepository.findById(diaryLikeSaveReqDto.diaryId())
                .orElseThrow(() -> new IllegalArgumentException("일기가 존재하지 않습니다."));
        User user = userRepository.findById(diaryLikeSaveReqDto.userId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        if (diaryLikeRepository.findByDiaryIdAndUserId(diary.getId(), user.getId()).isPresent()) {
            throw new IllegalArgumentException("일기 좋아요가 이미 존재합니다.");
        }

        DiaryLike diaryLike = DiaryLike.builder()
                .diary(diary)
                .user(user)
                .createdAt(new Date())
                .build();

        diaryLikeRepository.save(diaryLike);
        return DiaryLikeInfoResDto.from(diaryLike);
    }

    // 좋아요 삭제
    public void deleteLike(Long diaryId, Long userId) {
        DiaryLike diaryLike = diaryLikeRepository.findByDiaryIdAndUserId(diaryId, userId)
                .orElseThrow(() -> new IllegalArgumentException("일기 좋아요가 존재하지 않습니다."));
        diaryLikeRepository.delete(diaryLike);
    }

    // 좋아요 카운트
    public Long countLikes(Long diaryId) {
        return diaryLikeRepository.countByDiaryId(diaryId);
    }
}
