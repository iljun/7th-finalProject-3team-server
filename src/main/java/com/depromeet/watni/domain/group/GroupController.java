package com.depromeet.watni.domain.group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.watni.domain.accession.Accession;
import com.depromeet.watni.domain.accession.AccessionService;
import com.depromeet.watni.domain.accession.dto.AccessionResponseDto;
import com.depromeet.watni.domain.group.dto.AccessGroupRequestDto;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.group.dto.GroupResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GroupController {
	private final GroupService groupService;
	private final AccessionService accessionService;

	@GetMapping("api/groups")
	public GroupResponseDto getGroup(@RequestBody Long groupId) {
		return groupService.getGroup(groupId).toResponseDto();
	}

	@PostMapping("api/groups")
	public GroupResponseDto createGroup(@RequestBody GroupDto groupDto) {
		Group group = groupService.createGroup(groupDto);
		return group.toResponseDto();
	}

	@PostMapping("api/groups/access")
	public List<AccessionResponseDto> accessGroupByCode(@RequestBody AccessGroupRequestDto accessGroupRequestDto) {
		groupService.checkGroupCode(accessGroupRequestDto.getGroupId(), accessGroupRequestDto.getAccessCode());
		
		List<Accession> accessions = accessionService.accessGroupByCode(accessGroupRequestDto.getGroupId(),
				accessGroupRequestDto.getMemberIdList(), accessGroupRequestDto.getAccessionRole());
		
		List<AccessionResponseDto> result = new ArrayList<AccessionResponseDto>();
		for (Accession accession : accessions) {
			result.add(accession.toResponseDto());
		}
		return result;
	}
}