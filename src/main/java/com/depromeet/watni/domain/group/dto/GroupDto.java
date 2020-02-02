package com.depromeet.watni.domain.group.dto;

import com.depromeet.watni.domain.group.domain.Group;
import lombok.*;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Builder
public class GroupDto {
	private String groupName;
	private String description;

	public Group toEntity() {
		return Group
				.builder()
				.name(groupName)
				.description(description)
				.build();
	}
}
