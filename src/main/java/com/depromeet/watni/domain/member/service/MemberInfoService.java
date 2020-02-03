package com.depromeet.watni.domain.member.service;

import com.depromeet.watni.domain.accession.domain.Accession;
import com.depromeet.watni.domain.accession.service.AccessionService;
import com.depromeet.watni.domain.conference.dto.ConferenceMapper;
import com.depromeet.watni.domain.conference.dto.ConferenceResponseDto;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.service.GroupService;
import com.depromeet.watni.domain.manager.repository.ManagerRepository;
import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.domain.member.domain.Member;
import com.depromeet.watni.domain.member.dto.MemberInfoResponse;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberInfoService {
    private final static ConferenceMapper CONFERENCE_MAPPER = Mappers.getMapper(ConferenceMapper.class);
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

    @Transactional
    public List<MemberInfoResponse> getMemberInfo(MemberDetail memberDetail) {
        Member member = memberService.selectByMemberId(memberDetail.getMemberId());
        List<Accession> accessions = accessionService.findAccessionsByMember(memberDetail.getMemberId());
        List<MemberInfoResponse> memberInfoResponses= new ArrayList<MemberInfoResponse>();
        for (Accession accession: accessions) {
            Group participateGroup = accession.getGroup();
            List<ConferenceResponseDto> responseDtos = participateGroup.getConferences()
                    .stream()
                    .map(c -> CONFERENCE_MAPPER.converter(c))
                    .collect(Collectors.toList());

            MemberInfoResponse memberInfoResponse = MemberInfoResponse.builder().group(participateGroup.toResponseDto())
                    .isManager(managerRepository.findOneByGroupAndMember(accession.getGroup(),member).isPresent()).conferences(responseDtos).build();
            memberInfoResponses.add(memberInfoResponse);
        }
        return memberInfoResponses;
    }


}
