package com.likelion.plantication.plantGuide.application;

import com.likelion.plantication.global.exception.NotFoundException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.global.service.S3Service;
import com.likelion.plantication.plantGuide.api.dto.request.GuideSaveReqDto;
import com.likelion.plantication.plantGuide.api.dto.response.GuideDetailListResDto;
import com.likelion.plantication.plantGuide.api.dto.response.GuideDetailResDto;
import com.likelion.plantication.plantGuide.domain.PlantGuide;
import com.likelion.plantication.plantGuide.domain.repository.GuideRepository;
import com.likelion.plantication.user.domain.User;
import com.likelion.plantication.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GuideService {
    private final GuideRepository guideRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    // 도감 저장
    @Transactional
    public GuideDetailResDto guideSave(Long userId,
                                       GuideSaveReqDto guideSaveReqDto,
                                       MultipartFile multipartFile) throws IOException {
        String image = s3Service.upload(multipartFile, "guide");

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(
                        ErrorCode.USER_NOT_FOUND_EXCEPTION,
                        ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        PlantGuide guide = PlantGuide.builder()
                .user(user)
                .title(guideSaveReqDto.title())
                .sentence(guideSaveReqDto.sentence())
                .content(guideSaveReqDto.content())
                .image(image)
                .createdAt(new DateTime().toDate())
                .view(0)
                .build();

        guideRepository.save(guide);
        return GuideDetailResDto.from(guide);
    }

    // 도감 전체 조회(content 포함)
    @Transactional
    public GuideDetailListResDto guideFindAll() {
        List<PlantGuide> guides = guideRepository.findAll();

        List<GuideDetailResDto> guideDetailResDtoList = guides.stream()
                .map(GuideDetailResDto::from)
                .toList();
        return GuideDetailListResDto.from(guideDetailResDtoList);
    }

    // 도감 상세 조회
    @Transactional
    public GuideDetailResDto guideFindOne(Long guideId) {
        PlantGuide guide = guideRepository.findById(guideId).orElseThrow(
                () -> new NotFoundException(
                ErrorCode.GUIDE_NOT_FOUND_EXCEPTION,
                ErrorCode.GUIDE_NOT_FOUND_EXCEPTION.getMessage()));
        return GuideDetailResDto.from(guide);
    }

}
