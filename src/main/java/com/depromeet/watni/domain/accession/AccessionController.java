package com.depromeet.watni.domain.accession;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.watni.domain.accession.dto.AccessionDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccessionController {
	private final AccessionService accessionService;

	@PostMapping("api/accessions/auto/code")
	public List<Accession> accessGroupByManagers(@RequestBody AccessionDto accessionDto) {
		return accessionService.accessGroupByCode(accessionDto.getGroupId(), accessionDto.getMemberIdList(),accessionDto.getAccessionRole());
	}
	
}
