package com.likelion.plantication.diaryLike.application;

import com.likelion.plantication.diary.domain.Diary;
import com.likelion.plantication.diary.domain.repository.DiaryRepository;
import com.likelion.plantication.diaryLike.api.dto.request.DiaryLikeSaveReqDto;
import com.likelion.plantication.diaryLike.api.dto.response.DiaryLikeInfoResDto;
import com.likelion.plantication.diaryLike.domain.DiaryLike;
import com.likelion.plantication.diaryLike.domain.repository.DiaryLikeRepository;
import com.likelion.plantication.global.exception.AlreadyExistsException;
import com.likelion.plantication.global.exception.NotFoundException;
import com.likelion.plantication.global.exception.code.ErrorCode;
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
                .orElseThrow(() -> new NotFoundException(
                        ErrorCode.DIARY_NOT_FOUND_EXCEPTION,
                        ErrorCode.DIARY_NOT_FOUND_EXCEPTION.getMessage()));
        User user = userRepository.findById(diaryLikeSaveReqDto.userId())
                .orElseThrow(() -> new NotFoundException(
                        ErrorCode.USER_NOT_FOUND_EXCEPTION,
                        ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        if (diaryLikeRepository.findByDiaryIdAndUserId(diary.getId(), user.getId()).isPresent()) {
            throw new AlreadyExistsException(
                    ErrorCode.LIKE_ALREADY_EXISTS_EXCEPTION,
                    ErrorCode.LIKE_ALREADY_EXISTS_EXCEPTION.getMessage());
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
                .orElseThrow(() -> new NotFoundException(
                        ErrorCode.LIKE_NOT_FOUND_EXCEPTION,
                        ErrorCode.LIKE_NOT_FOUND_EXCEPTION.getMessage()));
        diaryLikeRepository.delete(diaryLike);
    }

    // 좋아요 카운트
    public Long countLikes(Long diaryId) {
        return diaryLikeRepository.countByDiaryId(diaryId);
    }
}
