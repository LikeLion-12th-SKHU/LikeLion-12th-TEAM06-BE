package com.likelion.plantication.plantGuide.api;

import com.likelion.plantication.global.exception.code.SuccessCode;
import com.likelion.plantication.plantGuide.api.dto.request.GuideSaveReqDto;
import com.likelion.plantication.plantGuide.api.dto.response.GuideDetailListResDto;
import com.likelion.plantication.plantGuide.api.dto.response.GuideDetailResDto;
import com.likelion.plantication.plantGuide.application.GuideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guide")
public class GuideController {
    private final GuideService guideService;

    // 도감 전체 조회(content 포함)
    @GetMapping("/content")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GuideDetailListResDto> guideFindAll() {
        GuideDetailListResDto guideDetailListResDto = guideService.guideFindAll();
        return ResponseEntity
                .status(SuccessCode.GET_SUCCESS.getHttpStatusCode())
                .body(guideDetailListResDto);
    }

    // 도감 상세 조회
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GuideDetailResDto> guideFindOne(@PathVariable("id") Long guideId) {
        GuideDetailResDto guideDetailResDto = guideService.guideFindOne(guideId);
        return ResponseEntity
                .status(SuccessCode.GET_SUCCESS.getHttpStatusCode())
                .body(guideDetailResDto);
    }

    // 도감 작성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GuideDetailResDto> guideSave(
            @RequestParam("user") Long userId,
            @RequestPart("guide")GuideSaveReqDto guideSaveReqDto,
            @RequestPart("image") MultipartFile image) throws IOException {
        GuideDetailResDto guideDetailResDto = guideService.guideSave(userId, guideSaveReqDto, image);
        return ResponseEntity
                .status(SuccessCode.POST_SAVE_SUCCESS.getHttpStatusCode())
                .body(guideDetailResDto);
    }
}
