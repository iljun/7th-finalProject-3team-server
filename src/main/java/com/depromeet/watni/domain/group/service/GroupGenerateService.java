package com.depromeet.watni.domain.group.service;

import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.group.repository.GroupRepository;
import com.depromeet.watni.domain.manager.service.ManagerService;
import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.domain.member.domain.Member;
import com.depromeet.watni.domain.member.service.MemberService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GroupGenerateService {
    private final ManagerService managerService;
    private final GroupRepository groupRepository;
    private final MemberService memberService;
    public GroupGenerateService(ManagerService managerService,
                                GroupRepository groupRepository,
                                MemberService memberService) {
        this.managerService = managerService;
        this.groupRepository = groupRepository;
        this.memberService = memberService;
    }

    @Transactional
    public Group createGroup(GroupDto groupDto, MemberDetail memberDetail) {
        Group group = groupDto.toEntity();
        group = groupRepository.save(group);
        Member member = memberService.selectByMemberId(memberDetail.getMemberId());
        managerService.registerManager(group, member);
        return group;
    }
}
