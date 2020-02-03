package com.depromeet.watni.domain.member.dto;

import com.depromeet.watni.domain.conference.dto.ConferenceResponseDto;
import com.depromeet.watni.domain.group.dto.GroupResponseDto;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class MemberInfoResponse {
    private boolean isManager;
    private GroupResponseDto group;
    private List<ConferenceResponseDto> conferences;
}
