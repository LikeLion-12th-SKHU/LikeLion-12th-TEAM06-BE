package com.likelion.plantication.diaryComment.application;

import com.likelion.plantication.diary.domain.Diary;
import com.likelion.plantication.diary.domain.repository.DiaryRepository;
import com.likelion.plantication.diaryComment.api.dto.request.DiaryCommentSaveReqDto;
import com.likelion.plantication.diaryComment.api.dto.request.DiaryCommentUpdateReqDto;
import com.likelion.plantication.diaryComment.api.dto.response.DiaryCommentInfoResDto;
import com.likelion.plantication.diaryComment.domain.DiaryComment;
import com.likelion.plantication.diaryComment.domain.repository.DiaryCommentRepository;
import com.likelion.plantication.global.exception.ForbiddenException;
import com.likelion.plantication.global.exception.NotFoundException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.user.domain.User;
import com.likelion.plantication.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryCommentService {
    private final DiaryCommentRepository diaryCommentRepository;
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    // 일기 댓글 생성
    @Transactional
    public DiaryCommentInfoResDto commentSave(Long diaryId, Long userId, DiaryCommentSaveReqDto diaryCommentSaveReqDto) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorCode.DIARY_NOT_FOUND_EXCEPTION,
                        ErrorCode.DIARY_NOT_FOUND_EXCEPTION.getMessage()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorCode.USER_NOT_FOUND_EXCEPTION,
                        ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        Date now = new DateTime().toDate();

        DiaryComment diaryComment = DiaryComment.builder()
                .diary(diary)
                .user(user)
                .content(diaryCommentSaveReqDto.content())
                .createdAt(now)
                .modifiedAt(now)
                .build();

        diaryCommentRepository.save(diaryComment);
        return DiaryCommentInfoResDto.from(diaryComment);
    }

    // 일기 댓글 수정
    @Transactional
    public DiaryCommentInfoResDto updateComment(Long commentId,
                                                Long userId,
                                                DiaryCommentUpdateReqDto diaryCommentUpdateReqDto
    ) throws IOException {
        DiaryComment diaryComment = diaryCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorCode.COMMENT_NOT_FOUND_EXCEPTION,
                        ErrorCode.COMMENT_NOT_FOUND_EXCEPTION.getMessage()));

        Long id = diaryComment.getUser() != null ? diaryComment.getUser().getUserId() : null;

        if (id == null || !id.equals(userId)) {
            throw new ForbiddenException(
                    ErrorCode.ACCESS_DENIED_EXCEPTION,
                    ErrorCode.ACCESS_DENIED_EXCEPTION.getMessage());
        }

        diaryComment.update(diaryCommentUpdateReqDto);
        diaryComment.updateModifiedAt(new DateTime().toDate());

        diaryCommentRepository.save(diaryComment);
        return DiaryCommentInfoResDto.from(diaryComment);
    }

    // 일기 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        DiaryComment diaryComment = diaryCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorCode.COMMENT_NOT_FOUND_EXCEPTION,
                        ErrorCode.COMMENT_NOT_FOUND_EXCEPTION.getMessage()));

        Long id = diaryComment.getUser() != null ? diaryComment.getUser().getUserId() : null;

        if (id == null || !id.equals(userId)) {
            throw new ForbiddenException(
                    ErrorCode.ACCESS_DENIED_EXCEPTION,
                    ErrorCode.ACCESS_DENIED_EXCEPTION.getMessage());
        }

        diaryCommentRepository.delete(diaryComment);
    }

}
