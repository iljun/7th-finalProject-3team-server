package com.depromeet.watni.security.filter;

import com.depromeet.watni.security.exception.UnauthorizedException;
import com.depromeet.watni.security.token.AuthToken;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final String HEADER_PREFIX = "Bearer ";

    public AuthFilter(RequestMatcher requestMatcher,
                      AuthenticationSuccessHandler authenticationSuccessHandler,
                      AuthenticationFailureHandler authenticationFailureHandler) {
        super(requestMatcher);
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String accessToken = this.getAccessToken(request.getHeader("Authorization"));

        return super.getAuthenticationManager().authenticate(new AuthToken(accessToken));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        this.authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }

    private String getAccessToken(String header) {
        if (StringUtils.isBlank(header) || header.length() < HEADER_PREFIX.length()) {
            throw new UnauthorizedException("invalid access_token");
        }

        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}
