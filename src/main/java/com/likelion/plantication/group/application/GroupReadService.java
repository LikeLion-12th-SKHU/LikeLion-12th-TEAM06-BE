package com.likelion.plantication.group.application;

import com.likelion.plantication.global.exception.CustomException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.global.exception.code.SuccessCode;
import com.likelion.plantication.group.api.dto.response.GroupResDto;
import com.likelion.plantication.group.domain.PlantGroup;
import com.likelion.plantication.group.domain.repository.GroupRepository;
import com.likelion.plantication.user.domain.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupReadService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public ResponseEntity<List<GroupResDto>> getAllGroups() {
        List<PlantGroup> groups = groupRepository.findAll();

        List<GroupResDto> groupResDtos = groups.stream()
                .map(GroupResDto::of)
                .collect(Collectors.toList());

        return ResponseEntity.status(SuccessCode.GROUP_GET_SUCCESS.getHttpStatus()).body(groupResDtos);
    }

    public ResponseEntity<GroupResDto> getGroupById(Long groupId) {
        PlantGroup groups = groupRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND_EXCEPTION, ErrorCode.GROUP_NOT_FOUND_EXCEPTION.getMessage()));

        // 성공 응답
        GroupResDto groupResDto = GroupResDto.of(groups);
        return ResponseEntity.status(SuccessCode.GROUP_GET_SUCCESS.getHttpStatus()).body(groupResDto);
    }
}
