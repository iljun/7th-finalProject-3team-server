package com.depromeet.watni;

import com.depromeet.watni.domain.member.Member;
import com.depromeet.watni.domain.member.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CommandRunner implements CommandLineRunner {

    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;

    public CommandRunner(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    @Profile("local")
    public void run(String... args) throws Exception {
        Member member = Member
                .builder()
                .name("testuser")
                .email("test@google.com")
                .password(passwordEncoder.encode("test1234"))
                .build();
        memberRepository.save(member);
    }
}
