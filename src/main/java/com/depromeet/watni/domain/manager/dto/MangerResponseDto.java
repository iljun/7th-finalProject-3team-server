package com.depromeet.watni.domain.manager.dto;

import com.depromeet.watni.domain.group.domain.Group;
import lombok.Setter;

@Setter
public class MangerResponseDto {
    private long managerId;
    private String email;
    private String username;
    private Group group;
}
