package com.likelion.plantication.diaryComment.api;

import com.likelion.plantication.diaryComment.api.dto.request.DiaryCommentSaveReqDto;
import com.likelion.plantication.diaryComment.api.dto.request.DiaryCommentUpdateReqDto;
import com.likelion.plantication.diaryComment.api.dto.response.DiaryCommentInfoResDto;
import com.likelion.plantication.diaryComment.application.DiaryCommentService;
import com.likelion.plantication.global.exception.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryCommentController {
    private final DiaryCommentService diaryCommentService;

    // 익명 일기 댓글 작성
    @PostMapping("/{diary_id}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DiaryCommentInfoResDto> commentSave(
            @PathVariable("diary_id") Long diaryId,
            @RequestBody DiaryCommentSaveReqDto diaryCommentSaveReqDto) {
        DiaryCommentInfoResDto diaryCommentInfoResDto = diaryCommentService.commentSave(diaryId, diaryCommentSaveReqDto);
        return ResponseEntity
                .status(SuccessCode.POST_SAVE_SUCCESS.getHttpStatusCode())
                .body(diaryCommentInfoResDto);
    }

    // 익명 일기 댓글 수정
    @PatchMapping("/{diary_id}/comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DiaryCommentInfoResDto> updateComment(
            @PathVariable("id") Long commentId,
            @RequestParam Long userId,
            @RequestBody DiaryCommentUpdateReqDto diaryCommentUpdateReqDto) throws IOException {
        DiaryCommentInfoResDto diaryCommentInfoResDto = diaryCommentService.updateComment(commentId, userId, diaryCommentUpdateReqDto);

        return ResponseEntity
                .status(SuccessCode.PATCH_UPDATE_SUCCESS.getHttpStatusCode())
                .body(diaryCommentInfoResDto);
    }

    // 익명 일기 댓글 삭제
    @DeleteMapping("/{diary_id}/comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long commentId,
                                                @RequestParam Long userId) {
        diaryCommentService.deleteComment(commentId, userId);

        return ResponseEntity
                .status(SuccessCode.DELETE_SUCCESS.getHttpStatusCode())
                .body("삭제되었습니다.");
    }
}
