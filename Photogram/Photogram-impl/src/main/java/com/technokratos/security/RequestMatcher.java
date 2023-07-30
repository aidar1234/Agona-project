package com.technokratos.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
public class RequestMatcher {

    private final String path;

    private final List<HttpMethod> methods;

    public boolean match(HttpServletRequest request) {
        if (!request.getServletPath().equals(path)) {
            return false;
        }
        for (HttpMethod method : methods) {
            if (request.getMethod().equals(method.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean matchStart(HttpServletRequest request) {
        if (!request.getServletPath().startsWith(path)) {
            return false;
        }
        for (HttpMethod method : methods) {
            if (request.getMethod().equals(method.toString())) {
                return true;
            }
        }
        return false;
    }
}
