package com.depromeet.watni.security.provider;

import com.depromeet.watni.config.TokenProperties;
import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.domain.member.MemberService;
import com.depromeet.watni.exception.BadRequestException;
import com.depromeet.watni.security.token.SignInToken;
import com.depromeet.watni.security.token.TokenService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class SignInProvider implements AuthenticationProvider {

    private MemberService memberService;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;
    private TokenProperties tokenProperties;
    public SignInProvider(MemberService memberService,
                          PasswordEncoder passwordEncoder,
                          TokenService tokenService,
                          TokenProperties tokenProperties) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.tokenProperties = tokenProperties;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SignInToken signIntoken = (SignInToken) authentication;
        String email = Optional.ofNullable(String.valueOf(signIntoken.getPrincipal())).orElseThrow(() -> new BadRequestException("email is required"));
        String password = Optional.ofNullable(String.valueOf(signIntoken.getCredentials())).orElseThrow(() -> new BadRequestException("password is required"));
        String clientId = signIntoken.getClientId();
        String clientSecret = signIntoken.getClientSecret();

        if (!clientId.equals(tokenProperties.getClientId())) {
            throw new BadRequestException("clientId is not matched");
        }
        if (!clientSecret.equals(tokenProperties.getClientSecret())) {
            throw new BadRequestException("clientSecrect is not matched");
        }
        MemberDetail memberDetail = (MemberDetail) memberService.loadUserByUsername(email);
        if (!passwordEncoder.matches(password, memberDetail.getPassword())) {
            throw new BadRequestException("password not matched");
        }
        tokenService.isDuplicateLogin(memberDetail);
        return new UsernamePasswordAuthenticationToken(memberDetail, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (SignInToken.class.isAssignableFrom(authentication));
    }
}
