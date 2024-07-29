package com.likelion.plantication.diaryLike.api.dto;

import com.likelion.plantication.diary.api.dto.request.DiarySaveReqDto;
import com.likelion.plantication.diary.api.dto.response.DiaryInfoResDto;
import com.likelion.plantication.diaryLike.api.dto.request.DiaryLikeSaveReqDto;
import com.likelion.plantication.diaryLike.api.dto.response.DiaryLikeInfoResDto;
import com.likelion.plantication.diaryLike.application.DiaryLikeService;
import com.likelion.plantication.global.exception.code.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryLikeController {
    private final DiaryLikeService diaryLikeService;

    // 일기 좋아요 생성
    @PostMapping("/{diaryId}/like")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DiaryLikeInfoResDto> addLike(
            @RequestBody DiaryLikeSaveReqDto diaryLikeSaveReqDto) {
        DiaryLikeInfoResDto diaryLikeInfoResDto = diaryLikeService.addLike(diaryLikeSaveReqDto);

        return ResponseEntity
                .status(SuccessCode.POST_SAVE_SUCCESS.getHttpStatusCode())
                .body(diaryLikeInfoResDto);
    }

    // 일기 좋아요 삭제
    @DeleteMapping("/{diaryId}/like")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteLike(@PathVariable Long diaryId, @RequestParam Long userId) {
        diaryLikeService.deleteLike(diaryId, userId);

        return ResponseEntity
                .status(SuccessCode.DELETE_SUCCESS.getHttpStatusCode())
                .body("삭제되었습니다.");
    }

    // 일기 카운트 조회
    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> countLikes(@RequestParam Long diaryId) {
        return ResponseEntity
                .status(SuccessCode.GET_SUCCESS.getHttpStatusCode())
                .body(diaryLikeService.countLikes(diaryId));
    }

}
