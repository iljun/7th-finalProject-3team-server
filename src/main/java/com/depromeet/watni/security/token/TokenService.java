package com.depromeet.watni.security.token;

import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.security.token.store.RedisTokenStore;
import com.depromeet.watni.utils.TokenGenerate;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
public class TokenService {

    private RedisTokenStore redisTokenStore;
    public TokenService(RedisTokenStore redisTokenStore) {
        this.redisTokenStore = redisTokenStore;
    }

    public TokenResult storeUserInfo(MemberDetail memberDetail) {
        String accessToken = TokenGenerate.generateAccessToken();
        String refreshToken = TokenGenerate.generateRefreshToken();

        redisTokenStore.storeAccessToken(accessToken, refreshToken, memberDetail);
        redisTokenStore.storeRefreshToken(refreshToken, accessToken, memberDetail);
        redisTokenStore.storeAuth(memberDetail, accessToken, refreshToken);
        return new TokenResult(accessToken, refreshToken);
    }

    public void isDuplicateLogin(MemberDetail memberDetail) {
        redisTokenStore.validDuplicateAuth(memberDetail);
    }

    @Getter
    @ToString
    public static class TokenResult {
        private String accessToken;
        private String refreshToken;

        public TokenResult(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
