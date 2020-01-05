package com.depromeet.watni.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class TokenRefresh extends UsernamePasswordAuthenticationToken {

    private GrantType grantType;
    private String clientId;
    private String clientSecret;
    private String accessToken;
    private String refreshToken;

    public TokenRefresh(GrantType grantType, String clientId, String clientSecret, String accessToken, String refreshToken) {
        this(accessToken, refreshToken);
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    private TokenRefresh(Object principal, Object credential) {
        super(principal, credential);
    }

    public GrantType getGrantType() {
        return grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
