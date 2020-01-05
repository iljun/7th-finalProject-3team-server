package com.depromeet.watni.security.provider;

import com.depromeet.watni.config.TokenProperties;
import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.exception.BadRequestException;
import com.depromeet.watni.security.exception.UnauthorizedException;
import com.depromeet.watni.security.token.TokenRefresh;
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
public class TokenProvider implements AuthenticationProvider {

    private TokenProperties tokenProperties;
    private TokenStore tokenStore;
    public TokenProvider(TokenProperties tokenProperties,
                         RedisTokenStore redisTokenStore) {
        this.tokenProperties = tokenProperties;
        this.tokenStore = redisTokenStore;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenRefresh tokenRefresh = (TokenRefresh) authentication;
        if (!tokenRefresh.getClientId().equals(tokenProperties.getClientId())) {
            throw new BadRequestException("clientId is not matched");
        }

        if (!tokenRefresh.getClientSecret().equals(tokenProperties.getClientSecret())) {
            throw new BadRequestException("clientSecret is not matched");
        }

        String refreshToken = tokenStore.readRefreshTokenFromAccessToken(tokenRefresh.getAccessToken());
        if (!refreshToken.equals(tokenRefresh.getRefreshToken())) {
            throw new UnauthorizedException("refreshToken is not matched");
        }
        MemberDetail memberDetail = tokenStore.readAuthenticationForRefreshToken(refreshToken);
        return new UsernamePasswordAuthenticationToken(memberDetail, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (TokenRefresh.class.isAssignableFrom(authentication));
    }
}
