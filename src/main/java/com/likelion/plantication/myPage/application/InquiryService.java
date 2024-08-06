package com.likelion.plantication.myPage.application;

import com.likelion.plantication.global.exception.ForbiddenException;
import com.likelion.plantication.global.exception.NotFoundException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.myPage.api.dto.request.InquirySaveReqDto;
import com.likelion.plantication.myPage.api.dto.response.InquiryInfoResDto;
import com.likelion.plantication.myPage.api.dto.response.InquiryListResDto;
import com.likelion.plantication.myPage.domain.Inquiry;
import com.likelion.plantication.myPage.domain.repository.InquiryRepository;
import com.likelion.plantication.user.domain.User;
import com.likelion.plantication.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    // 문의 저장
    @Transactional
    public InquiryInfoResDto inquirySave(InquirySaveReqDto inquirySaveReqDto,
                                         Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(
                        ErrorCode.USER_NOT_FOUND_EXCEPTION,
                        ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        Date now = DateTime.now().toDate();

        Inquiry inquiry = Inquiry.builder()
                .user(user)
                .subject(inquirySaveReqDto.subject())
                .content(inquirySaveReqDto.content())
                .createdAt(now)
                .build();

        inquiryRepository.save(inquiry);

        return InquiryInfoResDto.from(inquiry);
    }

    // 사용자 id로 사용자가 쓴 문의 조회
    @Transactional
    public InquiryListResDto inquiryFindByUserId(Long userId) {
        List<Inquiry> inquiries = inquiryRepository.findByUser_UserId(userId).orElseThrow(
                () -> new NotFoundException(
                        ErrorCode.INQUIRY_NOT_FOUND_EXCEPTION,
                        ErrorCode.INQUIRY_NOT_FOUND_EXCEPTION.getMessage()));

        List<InquiryInfoResDto> inquiryInfoResDtoList = inquiries.stream()
                .map(InquiryInfoResDto::from)
                .toList();

        return InquiryListResDto.from(inquiryInfoResDtoList);
    }

    // 문의 삭제
    @Transactional
    public void deleteInquiry(Long inquiryId, Long userId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(
                () -> new NotFoundException(
                        ErrorCode.INQUIRY_NOT_FOUND_EXCEPTION,
                        ErrorCode.INQUIRY_NOT_FOUND_EXCEPTION.getMessage()));

        Long id = inquiry.getUser().getUserId();

        if (!id.equals(userId)) {
            throw new ForbiddenException(
                    ErrorCode.ACCESS_DENIED_EXCEPTION,
                    ErrorCode.ACCESS_DENIED_EXCEPTION.getMessage());
        }

        inquiryRepository.delete(inquiry);
    }
}
