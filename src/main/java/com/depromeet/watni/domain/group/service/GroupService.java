package com.depromeet.watni.domain.group.service;

import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.repository.GroupRepository;
import com.depromeet.watni.exception.BadRequestException;
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
}
