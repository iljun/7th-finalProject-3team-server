package com.depromeet.watni.domain.group.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDto {
	private String groupName;
	private String code;
}
