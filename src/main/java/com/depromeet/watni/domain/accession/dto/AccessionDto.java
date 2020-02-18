package com.depromeet.watni.domain.accession.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class AccessionDto {

	private Long groupId;

	private String code;

	public AccessionDto(String code){
		this.code = code;
	}

	public AccessionDto(Long groupId){
		this.groupId = groupId;
	}
}
