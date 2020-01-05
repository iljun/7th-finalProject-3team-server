package com.depromeet.watni.security.filter;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class AuthRequestMatcher implements RequestMatcher {

    private OrRequestMatcher orRequestMatcher;
    private RequestMatcher requestMatcher;

    public AuthRequestMatcher(List<String> paths, String processingPath) {
        this.orRequestMatcher = new OrRequestMatcher(paths.stream().map(path -> new AntPathRequestMatcher(path)).collect(Collectors.toList()));
        this.requestMatcher = new AntPathRequestMatcher(processingPath);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        // public api only POST /api/member
        if (!orRequestMatcher.matches(request) && requestMatcher.matches(request)) {
            return request.getMethod().equals(HttpMethod.POST);
        }
        return false;
    }
}
