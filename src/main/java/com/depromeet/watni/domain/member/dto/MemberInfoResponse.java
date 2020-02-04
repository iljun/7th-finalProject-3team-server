package com.depromeet.watni.domain.member.dto;

import com.depromeet.watni.domain.group.dto.GroupResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class MemberInfoResponse {
    private GroupResponseDto group;
    private boolean isManager;

    public MemberInfoResponse(GroupResponseDto group,boolean isManager){
        this.group=group;
        this.isManager=isManager;
    }
}
