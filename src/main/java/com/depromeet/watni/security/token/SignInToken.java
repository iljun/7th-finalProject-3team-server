package com.depromeet.watni.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SignInToken extends UsernamePasswordAuthenticationToken {

    private GrantType grantType;
    private String clientId;
    private String clientSecret;
    private String email;
    private String password;

    public SignInToken(GrantType grantType, String clientId, String clientSecret, String email, String password) {
        this(email, password);
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.email = email;
        this.password = password;
    }

    private SignInToken(Object principal, Object credential) {
        super(principal, credential);
    }

    public GrantType getGrantType() {
        return this.grantType;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }
}
