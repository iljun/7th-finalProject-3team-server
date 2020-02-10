package com.depromeet.watni.domain.member.api;

import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.domain.member.domain.Member;
import com.depromeet.watni.domain.member.dto.MemberInfoResponse;
import com.depromeet.watni.domain.member.dto.MemberMapper;
import com.depromeet.watni.domain.member.dto.MemberRequestDto;
import com.depromeet.watni.domain.member.dto.UserMeResponse;
import com.depromeet.watni.domain.member.service.MemberInfoService;
import com.depromeet.watni.domain.member.service.MemberService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberApi {

    private static final MemberMapper MEMBER_MAPPER = Mappers.getMapper(MemberMapper.class);

    private MemberService memberService;
    private PasswordEncoder passwordEncoder;
    private MemberInfoService memberInfoService;
    public MemberApi(MemberService memberService,
                     PasswordEncoder passwordEncoder, MemberInfoService memberInfoService) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.memberInfoService = memberInfoService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity saveMember(@RequestBody @Validated MemberRequestDto memberRequestDto) {
        memberRequestDto.encodedPassword(this.passwordEncoder);
        Member member = memberService.createMember(memberRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/user/me")
    public ResponseEntity getMyInfo(@AuthenticationPrincipal MemberDetail memberDetail) {
        // TODO member additional Info
        Member member = memberService.selectByMemberId(memberDetail.getMemberId());
        List<MemberInfoResponse> details = memberInfoService.getMemberInfo(memberDetail);
        UserMeResponse result = new UserMeResponse(member.getEmail(),member.getName(),details);

        return ResponseEntity.ok(result);
    }
}
