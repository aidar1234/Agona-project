package com.technokratos.security.filter;

import com.technokratos.model.jooq.enums.AccountState;
import com.technokratos.security.AuthenticationPrinciple;
import com.technokratos.security.RequestMatcher;
import com.technokratos.security.authentication.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtStateFilter extends OncePerRequestFilter {

    private final List<RequestMatcher> matchers;
    private final List<RequestMatcher> prefixMatchers;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        for (RequestMatcher matcher : matchers) {
            if (matcher.match(request)) {
                if (authentication == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                if (hasInvalidState(response)) {
                    return;
                }
            }
        }

        for (RequestMatcher matcher : prefixMatchers) {
            if (matcher.matchStart(request)) {
                if (authentication == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                if (hasInvalidState(response)) {
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean hasInvalidState(HttpServletResponse response) throws IOException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        AuthenticationPrinciple principle = (AuthenticationPrinciple) jwtAuthentication.getPrincipal();

        if (principle.getState() == AccountState.NOT_CONFIRMED) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You have not verified your email");
            return true;
        }

        if (principle.getState() == AccountState.BANNED) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You have been banned");
            return true;
        }

        if (principle.getState() == AccountState.DELETED) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Your account has been deleted");
            return true;
        }
        return false;
    }
}
