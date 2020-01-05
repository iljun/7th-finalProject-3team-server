package com.depromeet.watni.domain.member;

import com.depromeet.watni.domain.member.dto.MemberRequestDto;
import com.depromeet.watni.security.token.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberApi {

    private MemberService memberService;
    private TokenService tokenService;
    private PasswordEncoder passwordEncoder;
    public MemberApi(MemberService memberService,
                     TokenService tokenService,
                     PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/member")
    public ResponseEntity saveMember(@RequestBody @Validated MemberRequestDto memberRequestDto) {
        memberRequestDto.encodedPassword(this.passwordEncoder);
        Member member = memberService.createMember(memberRequestDto);
        TokenService.TokenResult tokenResult = tokenService.storeUserInfo(new MemberDetail(member));
        return ResponseEntity.status(HttpStatus.CREATED).body(tokenResult);
    }
}
