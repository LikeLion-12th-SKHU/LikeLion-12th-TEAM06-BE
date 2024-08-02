package com.likelion.plantication.group.application;

import com.likelion.plantication.global.exception.CustomException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.global.exception.code.SuccessCode;
import com.likelion.plantication.group.api.dto.request.GroupUpdateReqDto;
import com.likelion.plantication.group.api.dto.response.GroupResDto;
import com.likelion.plantication.group.domain.Groups;
import com.likelion.plantication.group.domain.repository.GroupRepository;
import com.likelion.plantication.user.domain.User;
import com.likelion.plantication.user.domain.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupUDService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public ResponseEntity<GroupResDto> updateGroup(Long groupId, Long userId, GroupUpdateReqDto groupUpdateReqDto) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION, ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        Groups groups = groupRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND_EXCEPTION, ErrorCode.GROUP_NOT_FOUND_EXCEPTION.getMessage()));

        // 작성자와 로그인한 유저가 같은지 확인
        if (!groups.getUser().equals(user)) {
            throw new CustomException(ErrorCode.USER_NOT_AUTHORIZED, ErrorCode.USER_NOT_AUTHORIZED.getMessage());
        }

        // 그룹 업데이트
        groups.update(
                groupUpdateReqDto.title(),
                groupUpdateReqDto.openStatus(),
                groupUpdateReqDto.image(),
                groupUpdateReqDto.people(),
                groupUpdateReqDto.content(),
                groupUpdateReqDto.createdAt());
        groupRepository.save(groups);

        // 성공 응답
        GroupResDto groupResDto = GroupResDto.of(groups);
        return ResponseEntity.status(SuccessCode.GROUP_UPDATE_SUCCESS.getHttpStatus()).body(groupResDto);
    }

    @Transactional
    public ResponseEntity<Void> deleteGroup(Long groupId, Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION, ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        Groups groups = groupRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND_EXCEPTION, ErrorCode.GROUP_NOT_FOUND_EXCEPTION.getMessage()));

        // 작성자와 로그인한 유저가 같은지 확인
        if (!groups.getUser().equals(user)) {
            throw new CustomException(ErrorCode.USER_NOT_AUTHORIZED, ErrorCode.USER_NOT_AUTHORIZED.getMessage());
        }

        // 그룹 삭제
        groupRepository.delete(groups);

        // 성공 응답 반환
        return ResponseEntity.status(SuccessCode.CHALLENGE_UPDATE_SUCCESS.getHttpStatus()).build();
    }
}
