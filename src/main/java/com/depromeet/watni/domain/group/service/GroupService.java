package com.depromeet.watni.domain.group.service;

import com.depromeet.watni.domain.group.repository.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
}
