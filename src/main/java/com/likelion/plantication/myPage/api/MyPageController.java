package com.likelion.plantication.myPage.api;

import com.likelion.plantication.diary.api.dto.response.DiaryListResDto;
import com.likelion.plantication.diaryComment.api.dto.response.DiaryCommentListResDto;
import com.likelion.plantication.global.exception.code.SuccessCode;
import com.likelion.plantication.myPage.api.dto.request.InquirySaveReqDto;
import com.likelion.plantication.myPage.api.dto.response.InquiryInfoResDto;
import com.likelion.plantication.myPage.api.dto.response.InquiryListResDto;
import com.likelion.plantication.myPage.application.InquiryService;
import com.likelion.plantication.myPage.application.MyPageService;
import com.likelion.plantication.plantGuide.api.dto.response.GuideDetailListResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myPage")
public class MyPageController {
    private final MyPageService myPageService;
    private final InquiryService inquiryService;

    // 익명 일기 - 내 익명 일기 모아보기 (사용자 id로 사용자가 쓴 일기 조회)
    @GetMapping("/diaries")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DiaryListResDto> diariesFind(@RequestParam Long userId) {
        DiaryListResDto diaryListResDto = myPageService.diaryFindByUserId(userId);
        return ResponseEntity
                .status(SuccessCode.GET_SUCCESS.getHttpStatusCode())
                .body(diaryListResDto);
    }

    // 익명 일기 - 내가 작성한 댓글 (사용자 id로 사용자가 쓴 댓글 조회)
    @GetMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DiaryCommentListResDto> commentsFind(@RequestParam Long userId) {
        DiaryCommentListResDto diaryCommentListResDto = myPageService.commentFindByUserId(userId);
        return ResponseEntity
                .status(SuccessCode.GET_SUCCESS.getHttpStatusCode())
                .body(diaryCommentListResDto);
    }

    // 익명 일기 - 내 좋아요 모아보기 (사용자 id로 사용자가 쓴 좋아요 조회)
    @GetMapping("/likes")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DiaryListResDto> likedDiariesFind(@RequestParam Long userId) {
        DiaryListResDto diaryListResDto = myPageService.LikedDiaryFindByUserId(userId);
        return ResponseEntity
                .status(SuccessCode.GET_SUCCESS.getHttpStatusCode())
                .body(diaryListResDto);
    }

    // 식물 도감 - 내 식물 도감 모아보기 (사용자 id로 사용자가 쓴 식물 도감 조회) (content 포함)
    @GetMapping("/guideDetails")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GuideDetailListResDto> guideDetailsFind(@RequestParam Long userId) {
        GuideDetailListResDto guideDetailListResDto = myPageService.guideDetailFind(userId);
        return ResponseEntity
                .status(SuccessCode.GET_SUCCESS.getHttpStatusCode())
                .body(guideDetailListResDto);
    }

    // 문의하기 - 문의 작성하기
    @PostMapping("inquiries")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InquiryInfoResDto> inquirySave(@RequestBody InquirySaveReqDto inquirySaveReqDto,
                                                         @RequestParam Long userId) {
        InquiryInfoResDto inquiryInfoResDto = inquiryService.inquirySave(inquirySaveReqDto, userId);
        return ResponseEntity
                .status(SuccessCode.POST_SAVE_SUCCESS.getHttpStatusCode())
                .body(inquiryInfoResDto);
    }

    // 문의하기 - 내 문의 보기 (사용자 id로 사용자가 쓴 문의 조회)
    @GetMapping("/inquiries")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InquiryListResDto> inquiryFind(@RequestParam Long userId) {
        InquiryListResDto inquiryListResDto = inquiryService.inquiryFindByUserId(userId);
        return ResponseEntity
                .status(SuccessCode.GET_SUCCESS.getHttpStatusCode())
                .body(inquiryListResDto);
    }

    // 문의하기 - 문의 삭제하기
    @DeleteMapping("/{inquiryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteInquiry(@PathVariable("inquiryId") Long inquiryId,
                                                @RequestParam Long userId) {
        inquiryService.deleteInquiry(inquiryId, userId);
        return ResponseEntity
                .status(SuccessCode.DELETE_SUCCESS.getHttpStatusCode())
                .body("삭제되었습니다.");
    }
}
