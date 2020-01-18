package com.depromeet.watni.domain.group.service;

import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.group.repository.GroupRepository;
import com.depromeet.watni.exception.BadRequestException;
import com.depromeet.watni.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group selectGroupByGroupId(long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new BadRequestException("NOT FOUND GROUP"));
    }

    public Group createGroup(GroupDto groupDto) {
        Group group = Group.builder().name(groupDto.getGroupName()).build();
        return groupRepository.save(group);
    }

    public Group getGroup(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("NOT FOUND GROUP"));
    }
}
