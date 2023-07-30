package com.technokratos.security.filter;

import com.technokratos.model.jooq.enums.AccountRole;
import com.technokratos.security.RequestMatcher;
import com.technokratos.security.authentication.JwtAuthentication;
import com.technokratos.security.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

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
                if (!authorize(response)) {
                    return;
                }
                filterChain.doFilter(request, response);
            }
        }

        for (RequestMatcher matcher : prefixMatchers) {
            if (matcher.matchStart(request)) {
                if (authentication == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                if (!authorize(response)) {
                    return;
                }
                filterChain.doFilter(request, response);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean authorize(HttpServletResponse response) throws IOException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) jwtAuthentication.getDetails();
        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority(AccountRole.ADMIN.toString()))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;
    }
}
