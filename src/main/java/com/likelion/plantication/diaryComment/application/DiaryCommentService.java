package com.likelion.plantication.diaryComment.application;

import com.likelion.plantication.diary.domain.Diary;
import com.likelion.plantication.diary.domain.repository.DiaryRepository;
import com.likelion.plantication.diaryComment.api.dto.request.DiaryCommentSaveReqDto;
import com.likelion.plantication.diaryComment.api.dto.request.DiaryCommentUpdateReqDto;
import com.likelion.plantication.diaryComment.api.dto.response.DiaryCommentInfoResDto;
import com.likelion.plantication.diaryComment.domain.DiaryComment;
import com.likelion.plantication.diaryComment.domain.repository.DiaryCommentRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.Principal;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryCommentService {
    private final DiaryCommentRepository diaryCommentRepository;
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    // 일기 댓글 생성
    @Transactional
    public DiaryCommentInfoResDto commentSave(DiaryCommentSaveReqDto diaryCommentSaveReqDto) {
        Diary diary = diaryRepository.findById(diaryCommentSaveReqDto.diaryId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 일기가 존재하지 않습니다."));

        User user = userRepository.findById(diaryCommentSaveReqDto.userId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 존재하지 않습니다."));

        DateTime now = new DateTime();

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
    public DiaryCommentInfoResDto updateComment(DiaryCommentUpdateReqDto diaryCommentUpdateReqDto,
                                                Principal principal) throws IOException {
        DiaryComment diaryComment = diaryCommentRepository.findById(diaryCommentUpdateReqDto.id())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 일기 댓글을 찾을 수 없습니다."));

        Long id = diaryComment.getUser().getId(); // 수정하려는 일기의 사용자 id
        Long LoginId = Long.parseLong(principal.getName()); // 현재 삭제를 하려고 하는 사용자 id

        if (!id.equals(LoginId)) {
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }

        diaryComment.update(diaryCommentUpdateReqDto);
        diaryComment.updateModifiedAt(new DateTime());

        diaryCommentRepository.save(diaryComment);
        return DiaryCommentInfoResDto.from(diaryComment);
    }

    // 일기 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Principal principal) {
        DiaryComment diaryComment = diaryCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 일기 댓글이 없습니다." + commentId));

        Long id = diaryComment.getUser().getId(); // 삭제하려는 일기의 사용자 id
        Long LoginId = Long.parseLong(principal.getName()); // 현재 삭제를 하려고 하는 사용자 id

        if (!id.equals(LoginId)) {
            throw new IllegalArgumentException("댓글을 삭제할 권한이 없습니다.");
        }

        diaryCommentRepository.delete(diaryComment);
    }

}
