package com.depromeet.watni.security.filter;

import com.depromeet.watni.exception.BadRequestException;
import com.depromeet.watni.security.token.GrantType;
import com.depromeet.watni.security.token.SignInToken;
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

public class SignInFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler signInSuccessHandler;
    private AuthenticationFailureHandler signInFailureHandler;
    private ObjectMapper objectMapper;

    private SignInFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public SignInFilter(String defaultFilterProcessesUrl,
                        AuthenticationSuccessHandler signInSuccessHandler,
                        AuthenticationFailureHandler signInFailureHandler) {
        this(defaultFilterProcessesUrl);
        this.signInFailureHandler = signInFailureHandler;
        this.signInSuccessHandler = signInSuccessHandler;
        this.objectMapper = new ObjectMapper();
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
        String email = Optional.ofNullable(requestBody.get("email")).orElseThrow(() -> new BadRequestException("email is required"));
        String password = Optional.ofNullable(requestBody.get("password")).orElseThrow(() -> new BadRequestException("password is required"));
        SignInToken signInToken = new SignInToken(grantType, clientId, clientSecret, email, password);
        return super.getAuthenticationManager().authenticate(signInToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.signInSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        this.signInFailureHandler.onAuthenticationFailure(request, response, failed);
    }
}
