package com.likelion.plantication.myPage.application;

import com.likelion.plantication.diary.api.dto.response.DiaryInfoResDto;
import com.likelion.plantication.diary.api.dto.response.DiaryListResDto;
import com.likelion.plantication.diary.domain.Diary;
import com.likelion.plantication.diary.domain.repository.DiaryRepository;
import com.likelion.plantication.diaryComment.api.dto.response.DiaryCommentInfoResDto;
import com.likelion.plantication.diaryComment.api.dto.response.DiaryCommentListResDto;
import com.likelion.plantication.diaryComment.domain.DiaryComment;
import com.likelion.plantication.diaryComment.domain.repository.DiaryCommentRepository;
import com.likelion.plantication.diaryLike.domain.DiaryLike;
import com.likelion.plantication.diaryLike.domain.repository.DiaryLikeRepository;
import com.likelion.plantication.global.exception.NotFoundException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.plantGuide.api.dto.response.GuideDetailListResDto;
import com.likelion.plantication.plantGuide.api.dto.response.GuideDetailResDto;
import com.likelion.plantication.plantGuide.domain.PlantGuide;
import com.likelion.plantication.plantGuide.domain.repository.GuideRepository;
import com.likelion.plantication.user.domain.User;
import com.likelion.plantication.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {
    private final DiaryRepository diaryRepository;
    private final DiaryCommentRepository diaryCommentRepository;
    private final DiaryLikeRepository diaryLikeRepository;
    private final GuideRepository guideRepository;
    private final UserRepository userRepository;

    // 익명 일기 - 내 익명 일기 모아보기 (사용자 id로 사용자가 쓴 일기 조회)
    @Transactional
    public DiaryListResDto diaryFindByUserId(Long userId) {
        List<Diary> diaries = diaryRepository.findByUser_UserId(userId).orElseThrow(
                () -> new NotFoundException(
                        ErrorCode.DIARY_NOT_FOUND_EXCEPTION,
                        ErrorCode.DIARY_NOT_FOUND_EXCEPTION.getMessage()));

        // Diary객체를 DiaryInfoResDto로 변환
        List<DiaryInfoResDto> diaryInfoResDtoList = diaries.stream()
                .map(DiaryInfoResDto::from)
                .toList();

        return DiaryListResDto.from(diaryInfoResDtoList);
    }

    // 익명 일기 - 내가 작성한 댓글 (사용자 id로 사용자가 쓴 댓글 조회)
    @Transactional
    public DiaryCommentListResDto commentFindByUserId(Long userId) {
        List<DiaryComment> comments = diaryCommentRepository.findByUser_UserId(userId).orElseThrow(
                () -> new NotFoundException(
                        ErrorCode.COMMENT_NOT_FOUND_EXCEPTION,
                        ErrorCode.COMMENT_NOT_FOUND_EXCEPTION.getMessage()));

        List<DiaryCommentInfoResDto> diaryCommentInfoResDtoList = comments.stream()
                .map(DiaryCommentInfoResDto::from)
                .toList();

        return DiaryCommentListResDto.from(diaryCommentInfoResDtoList);
    }

    // 익명 일기 - 내 좋아요 모아보기 (사용자 id로 사용자가 쓴 좋아요 조회)
    @Transactional
    public DiaryListResDto LikedDiaryFindByUserId(Long userId) {

        List<DiaryLike> diaryLikes = diaryLikeRepository.findByUser_UserId(userId).orElseThrow(
                () -> new NotFoundException(
                        ErrorCode.LIKE_NOT_FOUND_EXCEPTION,
                        ErrorCode.LIKE_NOT_FOUND_EXCEPTION.getMessage()));

        List<DiaryInfoResDto> likedDiaries = diaryLikes.stream()
                .map(diaryLike -> diaryLike.getDiary())
                .map(DiaryInfoResDto::from)
                .toList();

        return DiaryListResDto.from(likedDiaries);
    }

    // 식물 도감 - 내 식물 도감 모아보기 (사용자 id로 사용자가 쓴 식물 도감 조회) (content 포함)
    @Transactional
    public GuideDetailListResDto guideDetailFind(Long userId) {
        List<PlantGuide> guides = guideRepository.findByUser_UserId(userId).orElseThrow(
                () -> new NotFoundException(
                        ErrorCode.GUIDE_NOT_FOUND_EXCEPTION,
                        ErrorCode.GUIDE_NOT_FOUND_EXCEPTION.getMessage()));

        List<GuideDetailResDto> guideDetailResDtoList = guides.stream()
                .map(GuideDetailResDto::from)
                .toList();

        return GuideDetailListResDto.from(guideDetailResDtoList);
    }

    // 사용자 정보 조회
    @Transactional
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.USER_NOT_FOUND_EXCEPTION,
                        ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));
    }
}
