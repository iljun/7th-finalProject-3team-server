package com.depromeet.watni.domain.accession.service;

import com.depromeet.watni.domain.accession.constant.AccessionStatus;
import com.depromeet.watni.domain.accession.constant.AccessionType;
import com.depromeet.watni.domain.accession.domain.Accession;
import com.depromeet.watni.domain.accession.repository.AccessionRepository;
import com.depromeet.watni.domain.group.service.GroupService;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.member.domain.Member;
import com.depromeet.watni.domain.member.service.MemberService;
import com.depromeet.watni.exception.NotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccessionService {
    private final AccessionRepository accessionRepository;
    private final GroupService groupService;
    private final MemberService memberService;
    public AccessionService(AccessionRepository accessionRepository,
                            GroupService groupService,
                            MemberService memberService) {
        this.accessionRepository = accessionRepository;
        this.groupService = groupService;
        this.memberService = memberService;
    }

    @Transactional
    public List<Accession> accessGroup(Long groupId, List<Long> memberIdList) {
        Group group = groupService.getGroup(groupId);
        List<Accession> accesionList = new ArrayList<Accession>();
        for (Long memberId : memberIdList) {
            Member member = memberService.selectByMemberId(memberId);
            Accession accession = Accession.builder().accessionType(AccessionType.AUTO)
                    .accessionStatus(AccessionStatus.ACCEPT).group(group).member(member).build();
            accesionList.add(accession);
        }
        return accessionRepository.saveAll(accesionList);
    }

    public void deleteAccess (Long accessId){
        accessionRepository.findById(accessId).orElseThrow(()->new NotFoundException("NOT FOUND ACCESS INFO"));
    }

    @Transactional
    public void deleteAccess (Long groupId,Long memberId){
        Group group = groupService.getGroup(groupId);
        Member member = memberService.selectByMemberId(memberId);
        Accession accession = accessionRepository.findOneByGroupAndMember(group,member).orElseThrow(()->new NotFoundException("NOT FOUND ACCESS INFO"));
        accessionRepository.delete(accession);
    }

    @Transactional
    public List<Accession> findAccessionsByMember(Long memberId){
        Member member = memberService.selectByMemberId(memberId);
        List<Accession> accessions = accessionRepository.findAllByMember(member);
        return accessions;
    }

}
