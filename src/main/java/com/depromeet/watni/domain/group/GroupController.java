package com.depromeet.watni.domain.group;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.watni.domain.accession.AccessionService;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.group.dto.GroupResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GroupController {
	private final GroupService groupService;
	private final AccessionService accessionService;
	
	@PostMapping("api/groups")
	public GroupResponseDto createGroup(@RequestBody GroupDto groupDto) {
		Group group = groupService.createGroup(groupDto);
		return group.toResponseDto();
	}
}