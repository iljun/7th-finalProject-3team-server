package com.depromeet.watni.domain.accession.dto;

import java.util.List;

import com.depromeet.watni.domain.accession.AccessionRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessionDto {
	private Long groupId;
	private List<Long> memberIdList;
	private AccessionRole accessionRole;
}
