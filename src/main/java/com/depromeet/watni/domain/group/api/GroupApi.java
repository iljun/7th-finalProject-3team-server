package com.depromeet.watni.domain.group.api;

import com.depromeet.watni.domain.group.service.GroupService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupApi {
    private final GroupService groupService;
    public GroupApi(GroupService groupService) {
        this.groupService = groupService;
    }

}
