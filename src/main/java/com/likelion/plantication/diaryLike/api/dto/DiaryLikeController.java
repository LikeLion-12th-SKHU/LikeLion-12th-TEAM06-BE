package com.likelion.plantication.diaryLike.api.dto;

import com.likelion.plantication.diaryLike.api.dto.request.DiaryLikeSaveReqDto;
import com.likelion.plantication.diaryLike.api.dto.response.DiaryLikeInfoResDto;
import com.likelion.plantication.diaryLike.application.DiaryLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryLikeController {
    private final DiaryLikeService diaryLikeService;

    // 일기 좋아요 생성
    @PostMapping("/{diaryId}/like")
    public DiaryLikeInfoResDto addLike(
            @RequestBody DiaryLikeSaveReqDto diaryLikeSaveReqDto) {
        return diaryLikeService.addLike(diaryLikeSaveReqDto);
    }

    // 일기 좋아요 삭제
    @DeleteMapping("/{diaryId}/like")
    public void deleteLike(@RequestParam Long diaryId, @RequestParam Long userId) {
        diaryLikeService.deleteLike(diaryId, userId);
    }

    // 일기 카운트
    @GetMapping("/count")
    public Long countLkes(@RequestParam Long diaryId) {
        return diaryLikeService.countLikes(diaryId);
    }

}
