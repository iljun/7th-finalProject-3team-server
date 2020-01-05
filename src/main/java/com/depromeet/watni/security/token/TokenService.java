package com.depromeet.watni.security.token;

import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.exception.BadRequestException;
import com.depromeet.watni.security.token.store.RedisTokenStore;
import com.depromeet.watni.utils.TokenGenerate;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
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

    public TokenResult refreshAccessToken(MemberDetail memberDetail, String originAccessToken, String refreshToken) {
        // origin accesstoken을 이용해 refreshToken search
        // 동일하다면 새로운 accessToken 저장 이후 retruen
        // refresh Token 만료일이 1주일 이하라면 새로운 token으로 재 갱신

        String originRefreshToken = redisTokenStore.readRefreshTokenFromAccessToken(originAccessToken);
        if (!originRefreshToken.equals(refreshToken)) {
            throw new BadRequestException("refreshToken is not matched");
        }

        String newAccessToken = TokenGenerate.generateAccessToken();
        long refreshTokenExpired = redisTokenStore.getExpiredTime(refreshToken);
        String newRefreshToken = null;
        // less than 7 days
        if (refreshTokenExpired < 60 * 60 * 24 * 7) {
            newRefreshToken = TokenGenerate.generateRefreshToken();
            redisTokenStore.deleteAuth(memberDetail);
            redisTokenStore.removeRefreshToken(refreshToken);
            redisTokenStore.storeAuth(memberDetail, newAccessToken, newRefreshToken);
            redisTokenStore.storeRefreshToken(newRefreshToken, newAccessToken, memberDetail);
            redisTokenStore.storeAccessToken(newAccessToken, newRefreshToken, memberDetail);
        } else {
            redisTokenStore.storeAccessToken(newAccessToken, refreshToken, memberDetail);
            redisTokenStore.updateRefreshToken(memberDetail, newAccessToken, refreshToken);
        }

        return new TokenResult(newAccessToken, newRefreshToken);
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TokenResult {
        private String accessToken;
        private String refreshToken;

        public TokenResult(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
