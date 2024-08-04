package com.likelion.plantication.myPage.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record InquiryListResDto(
        List<InquiryInfoResDto> inquiries
) {
    public static InquiryListResDto from(List<InquiryInfoResDto> inquiries) {
        return InquiryListResDto.builder()
                .inquiries(inquiries)
                .build();
    }
}
