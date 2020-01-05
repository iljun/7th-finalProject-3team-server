package com.depromeet.watni.security.handler;

import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.security.token.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenSuccessHandler implements AuthenticationSuccessHandler {

    private TokenService tokenService;
    private ObjectMapper objectMapper;
    public TokenSuccessHandler(TokenService tokenService) {
        this.tokenService = tokenService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        TokenService.TokenResult tokenResult = tokenService.storeUserInfo((MemberDetail) authentication.getPrincipal());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(objectMapper.writeValueAsString(tokenResult));

    }
}
