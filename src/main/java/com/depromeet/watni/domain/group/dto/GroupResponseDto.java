package com.depromeet.watni.domain.group.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class GroupResponseDto {
	private long groupId;

	private String name;
	
//	@Builder.Default
//	private List<Conference> conferences = new ArrayList<Conference>();

//	@Builder.Default
//	private List<Accession> accessions = new ArrayList<Accession>();

}
