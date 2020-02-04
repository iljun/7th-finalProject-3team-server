package com.depromeet.watni.domain.group.dto;

import com.depromeet.watni.domain.conference.dto.ConferenceResponseDto;
import com.depromeet.watni.domain.group.domain.Group;
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
	private List<ConferenceResponseDto> conferences = new ArrayList<ConferenceResponseDto>();
	//	@Builder.Default
    //	private List<Accession> accessions = new ArrayList<Accession>();

	public GroupResponseDto(Group group){
		this.groupId = group.getGroupId();
		this.name = group.getName();
		this.conferences = group.getConferences().stream().map(conference -> new ConferenceResponseDto(conference)).collect(Collectors.toList());
	}

}
