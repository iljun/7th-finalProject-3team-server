package com.depromeet.watni.domain.member.service;

import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.domain.member.repository.MemberRepository;
import com.depromeet.watni.domain.member.domain.Member;
import com.depromeet.watni.domain.member.dto.MemberRequestDto;
import com.depromeet.watni.exception.BadRequestException;
import com.depromeet.watni.exception.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements UserDetailsService {

    private MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("member not found"));
        return new MemberDetail(member);
    }

    public Member createMember(MemberRequestDto memberRequestDto) {
        boolean isExistEmail = memberRepository.findByEmail(memberRequestDto.getEmail()).isPresent();
        if (isExistEmail) {
            throw new BadRequestException("Already exists Email");
        }
        Member member = Member.of(memberRequestDto);
        member = memberRepository.save(member);
        return member;
    }

    public Member selectByMemberId(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("NOT FOUND MEMBER"));
    }
}
