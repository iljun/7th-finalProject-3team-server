package com.depromeet.watni.domain.group.dto;

import com.depromeet.watni.domain.conference.dto.ConferenceResponse;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.member.domain.Member;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class GroupResponseDto {
	private long groupId;

	private String name;
	
	@Builder.Default
	private List<ConferenceResponse> conferences = new ArrayList<>();

	public GroupResponseDto(Group group){
		this.groupId = group.getGroupId();
		this.name = group.getName();
		this.conferences = group.getConferences().stream().map(conference -> new ConferenceResponse(conference)).collect(Collectors.toList());
	}

	public GroupResponseDto(Group group, Member member){
		this.groupId = group.getGroupId();
		this.name = group.getName();
		this.conferences = group.getConferences().stream().map(conference -> new ConferenceResponse(conference,member)).collect(Collectors.toList());
	}

}
