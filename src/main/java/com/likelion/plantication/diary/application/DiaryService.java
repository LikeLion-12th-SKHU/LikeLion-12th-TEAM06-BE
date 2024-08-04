package com.likelion.plantication.diary.application;

import com.likelion.plantication.diary.api.dto.request.DiarySaveReqDto;
import com.likelion.plantication.diary.api.dto.request.DiaryUpdateReqDto;
import com.likelion.plantication.diary.api.dto.response.DiaryInfoResDto;
import com.likelion.plantication.diary.api.dto.response.DiaryListResDto;
import com.likelion.plantication.diary.domain.Diary;
import com.likelion.plantication.diary.domain.repository.DiaryRepository;
import com.likelion.plantication.global.exception.ForbiddenException;
import com.likelion.plantication.global.exception.NotFoundException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.global.service.S3Service;
import com.likelion.plantication.user.domain.User;
import com.likelion.plantication.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    // 일기 저장
    @Transactional
    public DiaryInfoResDto diarySave(DiarySaveReqDto diarySaveReqDto, MultipartFile multipartFile,
                                     Long userId) throws IOException {
        String image = s3Service.upload(multipartFile, "diary");

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(
                        ErrorCode.USER_NOT_FOUND_EXCEPTION,
                        ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        DateTime now = DateTime.now();
        Date nowDate = now.toDate();

        Diary diary = Diary.builder()
                .title(diarySaveReqDto.title())
                .content(diarySaveReqDto.content())
                .image(image)
                .createdAt(now)
                .modifiedAt(nowDate)
                .user(user)
                .build();

        diaryRepository.save(diary);

        return DiaryInfoResDto.from(diary);
    }

    // 일기 전체 조회
    @Transactional
    public DiaryListResDto diaryFindAll() {
        List<Diary> diaries = diaryRepository.findAll();

        List<DiaryInfoResDto> diaryInfoResDtoList = diaries.stream()
                .map(DiaryInfoResDto::from)
                .toList();

        return DiaryListResDto.from(diaryInfoResDtoList);
    }

    // diaryId로 일기 한 개 조회
    @Transactional
    public DiaryInfoResDto diaryFindOne(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new NotFoundException(
                        ErrorCode.DIARY_NOT_FOUND_EXCEPTION,
                        ErrorCode.DIARY_NOT_FOUND_EXCEPTION.getMessage()));

        return DiaryInfoResDto.from(diary);
    }

    // 일기 수정
    @Transactional
    public DiaryInfoResDto updateDiary(Long diaryId,
                                       DiaryUpdateReqDto diaryUpdateReqDto,
                                       MultipartFile image,
                                       Long userId) throws IOException {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new NotFoundException(
                        ErrorCode.DIARY_NOT_FOUND_EXCEPTION,
                        ErrorCode.DIARY_NOT_FOUND_EXCEPTION.getMessage()));

        Long id = diary.getUser().getUserId(); // 수정하려는 일기의 사용자 id

        if (!id.equals(userId)) {
            throw new ForbiddenException(
                    ErrorCode.ACCESS_DENIED_EXCEPTION,
                    ErrorCode.ACCESS_DENIED_EXCEPTION.getMessage());
        }
        diary.update(diaryUpdateReqDto);

        // 이미지가 있을 경우 이미지도 수정
        if (image != null && !image.isEmpty()) {
            String updateImage = s3Service.upload(image, "diary");
            diary.updateImage(updateImage);
        }

        // 수정일은 현재로 설정
        Date nowDate = new Date();
        diary.updateModifiedAt(nowDate);

        diaryRepository.save(diary);
        return DiaryInfoResDto.from(diary);
    }

    // 일기 삭제
    @Transactional
    public void deleteDiary(Long diaryId, Long userId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new NotFoundException(
                        ErrorCode.DIARY_NOT_FOUND_EXCEPTION,
                        ErrorCode.DIARY_NOT_FOUND_EXCEPTION.getMessage()));

        Long id = diary.getUser().getUserId(); // 삭제하려는 일기의 사용자 id

        if (!id.equals(userId)) {
            throw new ForbiddenException(
                    ErrorCode.ACCESS_DENIED_EXCEPTION,
                    ErrorCode.ACCESS_DENIED_EXCEPTION.getMessage());
        }
        diaryRepository.delete(diary);
    }
}
