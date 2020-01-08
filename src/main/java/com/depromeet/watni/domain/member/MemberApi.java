package com.depromeet.watni.domain.member;

import com.depromeet.watni.domain.member.dto.MemberRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberApi {

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
}
