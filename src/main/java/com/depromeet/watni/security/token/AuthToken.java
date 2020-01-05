package com.depromeet.watni.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthToken extends UsernamePasswordAuthenticationToken {

    private AuthToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public AuthToken(String accessToken) {
        this(accessToken, null);
    }
}
