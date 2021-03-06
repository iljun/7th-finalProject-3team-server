package com.depromeet.watni.domain.group.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Builder
public class AccessGroupRequestDto {
	private Long groupId;
	private String accessCode;
	private List<Long> memberIdList;
}
