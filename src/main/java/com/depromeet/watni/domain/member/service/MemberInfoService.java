package com.depromeet.watni.domain.member.service;

import com.depromeet.watni.domain.accession.domain.Accession;
import com.depromeet.watni.domain.accession.service.AccessionService;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.dto.GroupResponseDto;
import com.depromeet.watni.domain.group.service.GroupService;
import com.depromeet.watni.domain.manager.repository.ManagerRepository;
import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.domain.member.domain.Member;
import com.depromeet.watni.domain.member.dto.MemberInfoResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberInfoService {
    private final MemberService memberService;
    private final GroupService groupService;
    private final AccessionService accessionService;
    private final ManagerRepository managerRepository;

    public MemberInfoService(MemberService memberService, GroupService groupService, AccessionService accessionService, ManagerRepository managerRepository) {
        this.memberService = memberService;
        this.groupService = groupService;
        this.accessionService = accessionService;
        this.managerRepository = managerRepository;
    }

    public List<MemberInfoResponse> getMemberInfo(MemberDetail memberDetail) {
        Member member = memberService.selectByMemberId(memberDetail.getMemberId());
        List<Accession> accessions = accessionService.findAccessionsByMember(memberDetail.getMemberId());
        List<MemberInfoResponse> memberInfoResponses= new ArrayList<MemberInfoResponse>();
        for (Accession accession: accessions) {
            Group participateGroup = accession.getGroup();
            GroupResponseDto group = new GroupResponseDto(participateGroup);
            boolean isManager = managerRepository.findOneByGroupAndMember(participateGroup,member).isPresent();
            MemberInfoResponse memberInfo = new MemberInfoResponse(group,isManager);
            memberInfoResponses.add(memberInfo);
        }
        return memberInfoResponses;
    }
}
