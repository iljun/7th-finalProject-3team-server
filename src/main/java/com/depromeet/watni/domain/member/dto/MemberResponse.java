package com.depromeet.watni.domain.member.dto;

import com.depromeet.watni.domain.group.Group;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberResponse {
    private long userId;
    private String email;
    private String username;
    private Group group;
}
