package com.depromeet.watni.domain.accession.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class AccessionDto {

	@NotNull
	private List<Long> memberIdList;

	private Long groupId;

	private String code;

	public AccessionDto(List<Long> memberIdList,Long groupId,String code){
		this.memberIdList = memberIdList;
		this.groupId = groupId;
		this.code = code;
	}

	public AccessionDto(List<Long> memberIdList,Long groupId){
		this.memberIdList = memberIdList;
		this.groupId = groupId;
	}
}
