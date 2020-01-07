package com.depromeet.watni.domain.group;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.watni.domain.accession.AccessionService;
import com.depromeet.watni.domain.group.dto.GroupDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GroupController {
	private final GroupService groupService;
	private final AccessionService accessionService;
	
	@PostMapping("api/groups")
	public Group createGroup(@RequestBody GroupDto groupDto) {
		return groupService.createGroup(groupDto);
	}
}
