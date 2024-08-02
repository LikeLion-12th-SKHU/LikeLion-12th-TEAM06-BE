package com.likelion.plantication.group.application;

import com.likelion.plantication.global.exception.CustomException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.global.exception.code.SuccessCode;
import com.likelion.plantication.group.api.dto.request.GroupCreateReqDto;
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
public class GroupCreateService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public ResponseEntity<GroupResDto> createGroup (
            GroupCreateReqDto groupCreateReqDto, Long userId ) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION, ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage()));

        Groups groups = groupCreateReqDto.toEntity(user);
        groupRepository.save(groups);

        GroupResDto groupResDto = GroupResDto.of(groups);


        // 여기 body 이상...
        return ResponseEntity.status(SuccessCode.GROUP_CREATE_SUCCESS.getHttpStatus())
                .body(groupResDto);
    }
}
