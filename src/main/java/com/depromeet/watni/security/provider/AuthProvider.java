package com.depromeet.watni.security.provider;

import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.security.exception.UnauthorizedException;
import com.depromeet.watni.security.token.AuthToken;
import com.depromeet.watni.security.token.store.RedisTokenStore;
import com.depromeet.watni.security.token.store.TokenStore;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AuthProvider implements AuthenticationProvider {

    private TokenStore redisTokenStore;
    public AuthProvider(RedisTokenStore redisTokenStore) {
        this.redisTokenStore = redisTokenStore;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthToken authToken = (AuthToken) authentication;
        String accessToken = (String) authToken.getPrincipal();
        MemberDetail memberDetail = redisTokenStore.readAuthentication(accessToken);
        if (memberDetail == null) {
            String refreshToken = redisTokenStore.readRefreshTokenFromAccessToken(accessToken);
            if (refreshToken != null) {
                throw new UnauthorizedException("access_token is expired");
            }
            throw new UnauthorizedException("invalid access_token");
        }

        return new UsernamePasswordAuthenticationToken(memberDetail, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (AuthToken.class.isAssignableFrom(authentication));
    }
}
