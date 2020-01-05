package com.depromeet.watni.security.filter;

import com.depromeet.watni.exception.BadRequestException;
import com.depromeet.watni.security.token.GrantType;
import com.depromeet.watni.security.token.TokenRefresh;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class TokenFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private ObjectMapper objectMapper;

    public TokenFilter(String defaultFilterProcessesUrl,
                       AuthenticationSuccessHandler authenticationSuccessHandler,
                       AuthenticationFailureHandler authenticationFailureHandler) {
        this(defaultFilterProcessesUrl);
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.objectMapper = new ObjectMapper();
    }

    private TokenFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals(HttpMethod.POST.name())) {
            // TODO
            throw new RuntimeException("");
        }

        Map<String, String> requestBody = objectMapper.readValue(request.getReader(), Map.class);
        GrantType grantType = GrantType.valueOf(Optional.ofNullable(requestBody.get("grantType")).orElseThrow(() -> new BadRequestException("grantType is required")));
        String clientId = Optional.ofNullable(requestBody.get("clientId")).orElseThrow(() -> new BadRequestException("clientId is required"));
        String clientSecret = Optional.ofNullable(requestBody.get("clientSecret")).orElseThrow(() -> new BadRequestException("clientSecret id required"));
        String accessToken = Optional.ofNullable(requestBody.get("accessToken")).orElseThrow(() -> new BadRequestException("accessToken is required"));
        String refreshToken = Optional.ofNullable(requestBody.get("refreshToken")).orElseThrow(() -> new BadRequestException("refreshToken is required"));
        TokenRefresh tokenRefresh = new TokenRefresh(grantType, clientId, clientSecret, accessToken, refreshToken);
        return super.getAuthenticationManager().authenticate(tokenRefresh);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        this.authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
    }
}
