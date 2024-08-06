package com.likelion.plantication.diary.api.dto.request;

import java.util.Date;

public record DiaryUpdateReqDto(
        String title,
        String content
) {

}
