package com.likelion.plantication.plantGuide.api;

import com.likelion.plantication.diary.api.dto.request.DiarySaveReqDto;
import com.likelion.plantication.diary.api.dto.response.DiaryInfoResDto;
import com.likelion.plantication.diary.api.dto.response.DiaryListResDto;
import com.likelion.plantication.global.exception.code.SuccessCode;
import com.likelion.plantication.plantGuide.api.dto.request.GuideSaveReqDto;
import com.likelion.plantication.plantGuide.api.dto.response.GuideDetailListResDto;
import com.likelion.plantication.plantGuide.api.dto.response.GuideDetailResDto;
import com.likelion.plantication.plantGuide.api.dto.response.GuideListResDto;
import com.likelion.plantication.plantGuide.application.GuideService;
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
@RequestMapping("/guide")
public class GuideController {
    private final GuideService guideService;

    // 도감 전체 조회(content 미포함)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GuideListResDto> guideFind() {
        GuideListResDto guideListResDto = guideService.guideFind();
        return ResponseEntity
                .status(SuccessCode.GET_SUCCESS.getHttpStatusCode())
                .body(guideListResDto);
    }

    // 도감 전체 조회(content 포함)
    @GetMapping("/content")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GuideDetailListResDto> guideFindAll() {
        GuideDetailListResDto guideDetailListResDto = guideService.guideFindAll();
        return ResponseEntity
                .status(SuccessCode.GET_SUCCESS.getHttpStatusCode())
                .body(guideDetailListResDto);
    }

    // 도감 작성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GuideDetailResDto> guideSave(
            @RequestPart("guide")GuideSaveReqDto guideSaveReqDto,
            @RequestPart("image") MultipartFile image,
            Principal principal) throws IOException {
        GuideDetailResDto guideDetailResDto = guideService.guideSave(guideSaveReqDto, image, principal);
        return ResponseEntity
                .status(SuccessCode.POST_SAVE_SUCCESS.getHttpStatusCode())
                .body(guideDetailResDto);
    }
}
