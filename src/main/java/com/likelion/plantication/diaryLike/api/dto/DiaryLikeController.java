package com.likelion.plantication.diaryLike.api.dto;

import com.likelion.plantication.diaryLike.api.dto.response.DiaryLikeInfoResDto;
import com.likelion.plantication.diaryLike.application.DiaryLikeService;
import com.likelion.plantication.global.exception.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
public class DiaryLikeController {
    private final DiaryLikeService diaryLikeService;

    // 일기 좋아요 생성
    @PostMapping("/{diary_id}/like")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DiaryLikeInfoResDto> addLike(
            @PathVariable("diary_id") Long diaryId) {
        DiaryLikeInfoResDto diaryLikeInfoResDto = diaryLikeService.addLike(diaryId);

        return ResponseEntity
                .status(SuccessCode.POST_SAVE_SUCCESS.getHttpStatusCode())
                .body(diaryLikeInfoResDto);
    }

    // 일기 좋아요 삭제
    @DeleteMapping("/{diary_id}/like")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteLike(@PathVariable("diary_id") Long diaryId,
                                             @RequestParam Long userId) {
        diaryLikeService.deleteLike(diaryId, userId);

        return ResponseEntity
                .status(SuccessCode.DELETE_SUCCESS.getHttpStatusCode())
                .body("삭제되었습니다.");
    }

    // 일기 카운트 조회
    @GetMapping("/{diary_id}/count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> countLikes(@PathVariable("diary_id") Long diaryId) {
        return ResponseEntity
                .status(SuccessCode.GET_SUCCESS.getHttpStatusCode())
                .body(diaryLikeService.countLikes(diaryId));
    }

}
