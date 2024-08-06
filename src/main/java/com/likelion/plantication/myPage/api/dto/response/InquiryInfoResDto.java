package com.likelion.plantication.myPage.api.dto.response;

import com.likelion.plantication.diary.api.dto.response.DiaryInfoResDto;
import com.likelion.plantication.diary.domain.Diary;
import com.likelion.plantication.myPage.domain.Inquiry;
import lombok.Builder;
import org.joda.time.DateTime;

import java.util.Date;

@Builder
public record InquiryInfoResDto(
    Long id,
    Long userId,
    String subject,
    String content,
    Date createdAt

) {
    public static InquiryInfoResDto from(Inquiry inquiry) {
        return InquiryInfoResDto.builder()
                .id(inquiry.getId())
                .userId(inquiry.getUser().getUserId())
                .subject(inquiry.getSubject())
                .content(inquiry.getContent())
                .createdAt(inquiry.getCreatedAt())
                .build();
    }
}
