package com.depromeet.watni.security.token.store;

import com.depromeet.watni.config.TokenProperties;
import com.depromeet.watni.domain.member.MemberDetail;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisTokenStore implements TokenStore {

    private RedisTemplate redisTemplate;
    private TokenProperties tokenProperties;
    public RedisTokenStore(RedisTemplate redisTemplate,
                           TokenProperties tokenProperties) {
        this.redisTemplate = redisTemplate;
        this.tokenProperties = tokenProperties;
    }

    private final String ACCESS_TO_REFRESH = "access_to_refresh:";
    private final String ACCESS_TO_AUTH = "access_to_auth:";
    private final String REFRESH_TO_ACCESS = "refresh_to_access:";
    private final String REFRESH_TO_AUTH = "refresh_to_auth:";
    private final String AUTH_TO_ACCESS = "auth_to_access:";
    private final String AUTH_TO_REFRESH = "refresh_to_access:";

    @Override
    public MemberDetail readAuthentication(String accessToken) {
        ValueOperations<String, MemberDetail> value = redisTemplate.opsForValue();
        MemberDetail memberDetail = value.get(ACCESS_TO_AUTH + accessToken);
        return memberDetail;
    }

    @Override
    public void storeAccessToken(String accessToken, String refreshToken, MemberDetail authentication) {
        ValueOperations<String, String> accessTokenToRefreshToken = redisTemplate.opsForValue();
        accessTokenToRefreshToken.set(ACCESS_TO_REFRESH + accessToken, refreshToken, tokenProperties.getAccessTokenExpired(), TimeUnit.SECONDS);
        ValueOperations<String, MemberDetail> accessTokenToAuth = redisTemplate.opsForValue();
        accessTokenToAuth.set(ACCESS_TO_AUTH + accessToken, authentication, tokenProperties.getAccessTokenExpired(), TimeUnit.SECONDS);
    }

    @Override
    public void removeAccessToken(String accessToken) {
        redisTemplate.delete(ACCESS_TO_AUTH + accessToken);
        redisTemplate.delete(ACCESS_TO_REFRESH + accessToken);
    }

    @Override
    public String readRefreshTokenFromAccessToken(String accessToken) {
        ValueOperations<String, String> refreshTokenToAccessToken = redisTemplate.opsForValue();
        String refreshToken = refreshTokenToAccessToken.get(ACCESS_TO_REFRESH + accessToken);
        return refreshToken;
    }

    @Override
    public void storeRefreshToken(String refreshToken, String accessToken, MemberDetail authentication) {
        ValueOperations<String, String> refreshTokenToAccessToken = redisTemplate.opsForValue();
        refreshTokenToAccessToken.set(REFRESH_TO_ACCESS + refreshToken, accessToken, tokenProperties.getRefreshTokenExpired(), TimeUnit.SECONDS);
        ValueOperations<String, MemberDetail> refreshTokenToAuth = redisTemplate.opsForValue();
        refreshTokenToAuth.set(REFRESH_TO_AUTH + refreshToken, authentication, tokenProperties.getRefreshTokenExpired(), TimeUnit.SECONDS);
    }

    @Override
    public MemberDetail readAuthenticationForRefreshToken(String refreshToken) {
        ValueOperations<String, MemberDetail> value = redisTemplate.opsForValue();
        MemberDetail memberDetail = value.get(REFRESH_TO_AUTH + refreshToken);
        return memberDetail;
    }

    @Override
    public void removeRefreshToken(String refreshToken) {
        redisTemplate.delete(REFRESH_TO_AUTH + refreshToken);
        redisTemplate.delete(REFRESH_TO_ACCESS + refreshToken);
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(String refreshToken) {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        String accessToken = value.get(ACCESS_TO_REFRESH + refreshToken);
        redisTemplate.delete(ACCESS_TO_REFRESH + accessToken);
        redisTemplate.delete(ACCESS_TO_AUTH + accessToken);
    }

    @Override
    public void storeAuth(MemberDetail authentication, String accessToken, String refreshToken) {
        ValueOperations<String, String> authToAccessToken = redisTemplate.opsForValue();
        authToAccessToken.set(AUTH_TO_ACCESS + authentication.toString(), accessToken, tokenProperties.getRefreshTokenExpired(), TimeUnit.SECONDS);
        ValueOperations<String, String> authToRefreshToken = redisTemplate.opsForValue();
        authToRefreshToken.set(AUTH_TO_REFRESH + authentication, refreshToken, tokenProperties.getRefreshTokenExpired(), TimeUnit.SECONDS);
    }

    @Override
    public void deleteAuth(MemberDetail authentication) {
        redisTemplate.delete(AUTH_TO_ACCESS + authentication);
        redisTemplate.delete(AUTH_TO_REFRESH + authentication);
    }

    @Override
    public void validDuplicateAuth(MemberDetail authentication) {
        if (redisTemplate.hasKey(AUTH_TO_ACCESS + authentication) || redisTemplate.hasKey(AUTH_TO_REFRESH + authentication)) {
            String accessToken = redisTemplate.opsForValue().get(AUTH_TO_ACCESS + authentication).toString();
            String refreshToken = redisTemplate.opsForValue().get(AUTH_TO_REFRESH + authentication).toString();
            redisTemplate.delete(AUTH_TO_ACCESS + authentication);
            redisTemplate.delete(AUTH_TO_REFRESH + authentication);
            redisTemplate.delete(ACCESS_TO_REFRESH + accessToken);
            redisTemplate.delete(ACCESS_TO_AUTH + accessToken);
            redisTemplate.delete(REFRESH_TO_ACCESS + refreshToken);
            redisTemplate.delete(REFRESH_TO_AUTH + refreshToken);
        }
    }
}
