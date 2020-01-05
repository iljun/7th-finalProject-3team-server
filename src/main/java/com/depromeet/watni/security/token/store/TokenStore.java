package com.depromeet.watni.security.token.store;

import com.depromeet.watni.domain.member.MemberDetail;

public interface TokenStore {

    MemberDetail readAuthentication(String accessToken);

    void storeAccessToken(String accessToken, String refreshToken, MemberDetail authentication);

    void removeAccessToken(String accessToken);

    String readRefreshTokenFromAccessToken(String accessToken);

    void storeRefreshToken(String refreshToken, String accessToken, MemberDetail authentication);

    MemberDetail readAuthenticationForRefreshToken(String refreshToken);

    void removeRefreshToken(String refreshToken);

    void removeAccessTokenUsingRefreshToken(String refreshToken);

    void storeAuth(MemberDetail authentication, String accessToken, String refreshToken);

    void deleteAuth(MemberDetail authentication);

    void validDuplicateAuth(MemberDetail authentication);
}
