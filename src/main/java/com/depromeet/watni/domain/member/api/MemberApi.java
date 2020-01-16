package com.depromeet.watni.domain.member.api;

import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.domain.member.domain.Member;
import com.depromeet.watni.domain.member.dto.MemberMapper;
import com.depromeet.watni.domain.member.dto.MemberRequestDto;
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

@RestController
public class MemberApi {

    private static final MemberMapper MEMBER_MAPPER = Mappers.getMapper(MemberMapper.class);

    private MemberService memberService;
    private PasswordEncoder passwordEncoder;
    public MemberApi(MemberService memberService,
                     PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
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
        return ResponseEntity.ok(MEMBER_MAPPER.convert(memberDetail));
    }
}
