package com.depromeet.watni.domain.group;

import org.springframework.stereotype.Service;

import com.depromeet.watni.domain.accession.AccessionRepository;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.member.MemberRepository;
import com.depromeet.watni.exception.BadRequestException;
import com.depromeet.watni.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {
	private final GroupRepository groupRepository;

	public boolean checkRedundantGroupCode(String code) {
		if (groupRepository.findOneByCode(code).isPresent())
			throw new BadRequestException("This code is redundant.");
		return true;
	}

	public Group createGroup(GroupDto groupDto) {
		checkRedundantGroupCode(groupDto.getCode());
		Group group = Group.builder().name(groupDto.getGroupName()).code(groupDto.getCode()).build();
		return groupRepository.save(group);
	}

	public Group getGroup(Long groupId) {
		return groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("NOT FOUND GROUP"));
	}
}
