package com.likelion.plantication.diary.api;

import com.likelion.plantication.diary.api.dto.request.DiarySaveReqDto;
import com.likelion.plantication.diary.api.dto.request.DiaryUpdateReqDto;
import com.likelion.plantication.diary.api.dto.response.DiaryInfoResDto;
import com.likelion.plantication.diary.api.dto.response.DiaryListResDto;
import com.likelion.plantication.diary.application.DiaryService;
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
public class DiaryController {
    private final DiaryService diaryService;

    // 익명 일기 전체 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DiaryListResDto> diaryFindAll() {
        DiaryListResDto diaryListResDto = diaryService.diaryFindAll();
        return ResponseEntity
                .status(SuccessCode.GET_SUCCESS.getHttpStatusCode())
                .body(diaryListResDto);
    }

    // 익명 일기 상세 조회 (diaryId로 일기 한 개 조회)
    @GetMapping("/{diaryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DiaryInfoResDto> diaryFindOne(@PathVariable Long diaryId) {
        DiaryInfoResDto diaryInfoResDto = diaryService.diaryFindOne(diaryId);
        return ResponseEntity
                .status(SuccessCode.GET_SUCCESS.getHttpStatusCode())
                .body(diaryInfoResDto);

    }

    // 익명 일기 작성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DiaryInfoResDto> diarySave(
            @RequestPart("diary")DiarySaveReqDto diarySaveReqDto,
            @RequestPart("image")MultipartFile image,
            Principal principal) throws IOException {
        DiaryInfoResDto diaryInfoResDto = diaryService.diarySave(diarySaveReqDto, image, principal);
        return ResponseEntity
                .status(SuccessCode.POST_SAVE_SUCCESS.getHttpStatusCode())
                .body(diaryInfoResDto);
    }

    // 익명 일기 수정
    @PatchMapping(value = "/{diaryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DiaryInfoResDto> updateDiary(
            @PathVariable("diaryId") Long diaryId,
            @RequestPart("diary")DiaryUpdateReqDto diaryUpdateReqDto,
            @RequestPart("image") MultipartFile image,
            Principal principal) throws IOException {
        DiaryInfoResDto diaryInfoResDto = diaryService.updateDiary(diaryId, diaryUpdateReqDto, image, principal);
        return ResponseEntity
                .status(SuccessCode.PATCH_UPDATE_SUCCESS.getHttpStatusCode())
                .body(diaryInfoResDto);
    }


    // 익명 일기 삭제
    @DeleteMapping("/{diaryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteDiary(@PathVariable("diaryId") Long diaryId,
                                              Principal principal) {
        diaryService.deleteDiary(diaryId, principal);
        return ResponseEntity
                .status(SuccessCode.DELETE_SUCCESS.getHttpStatusCode())
                .body("삭제되었습니다.");
    }

}
