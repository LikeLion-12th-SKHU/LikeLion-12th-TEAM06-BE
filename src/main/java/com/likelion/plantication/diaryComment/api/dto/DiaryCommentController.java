package com.likelion.plantication.diaryComment.api.dto;

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
    @PostMapping("/{diaryId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DiaryCommentInfoResDto> commentSave(@RequestBody DiaryCommentSaveReqDto diaryCommentSaveReqDto) {
        DiaryCommentInfoResDto diaryCommentInfoResDto = diaryCommentService.commentSave(diaryCommentSaveReqDto);
        return ResponseEntity
                .status(SuccessCode.POST_SAVE_SUCCESS.getHttpStatusCode())
                .body(diaryCommentInfoResDto);
    }

    // 익명 일기 댓글 수정
    @PatchMapping("/{diaryId}/comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DiaryCommentInfoResDto> updateComment(
            @RequestBody DiaryCommentUpdateReqDto diaryCommentUpdateReqDto,
            Principal principal) throws IOException {
        DiaryCommentInfoResDto diaryCommentInfoResDto = diaryCommentService.updateComment(diaryCommentUpdateReqDto, principal);

        return ResponseEntity
                .status(SuccessCode.PATCH_UPDATE_SUCCESS.getHttpStatusCode())
                .body(diaryCommentInfoResDto);
    }

    // 익명 일기 댓글 삭제
    @DeleteMapping("/{diaryId}/comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                Principal principal) {
        diaryCommentService.deleteComment(commentId, principal);

        return ResponseEntity
                .status(SuccessCode.DELETE_SUCCESS.getHttpStatusCode())
                .body("삭제되었습니다.");
    }
}
