package com.depromeet.watni.domain.manager;

import com.depromeet.watni.domain.group.GroupRepository;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.manager.domain.Manager;
import com.depromeet.watni.domain.member.Member;
import com.depromeet.watni.domain.member.MemberRepository;
import com.depromeet.watni.exception.BadRequestException;
import com.depromeet.watni.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerService {
	private final ManagerRepository managerRepository;
	private final GroupRepository groupRepository;
	private final MemberRepository memberRepository;
	
	public Manager registerManager (Long groupId, Long memberId) {
		Group group = groupRepository.findById(groupId).orElseThrow(()->new NotFoundException("NOT FOUND GROUP"));
		Member member = memberRepository.findById(memberId).orElseThrow(()->new NotFoundException("NOT FOUND MEMBER"));
		Manager manager = Manager.builder().group(group).memberId(memberId).build();
		return managerRepository.save(manager);
	}
	public void checkManager (Long groupId,Long memberId) {
		Group group = groupRepository.findById(groupId).orElseThrow(()->new NotFoundException("NOT FOUND GROUP"));
		Member member = memberRepository.findById(memberId).orElseThrow(()->new NotFoundException("NOT FOUND MEMBER"));
		managerRepository.findOneByGroupAndMemberId(group, memberId).orElseThrow(()-> new BadRequestException("NOT MANAGER"));
	}
	public void deleteManager (Long managerId) {
		Manager manager = managerRepository.findById(managerId).orElseThrow(()-> new NotFoundException("NOT FOUND MANAGER"));
		managerRepository.delete(manager);
	}
	
}
