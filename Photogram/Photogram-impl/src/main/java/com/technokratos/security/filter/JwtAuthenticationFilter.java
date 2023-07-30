package com.technokratos.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.technokratos.exception.UserNotFoundException;
import com.technokratos.model.jooq.tables.pojos.AccountEntity;
import com.technokratos.security.AuthenticationPrinciple;
import com.technokratos.security.JwtUtil;
import com.technokratos.security.RequestMatcher;
import com.technokratos.security.authentication.JwtAuthentication;
import com.technokratos.security.details.UserDetailsImpl;
import com.technokratos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHENTICATION_HEADER = "Authorization";

    private final List<RequestMatcher> matchers;
    private final List<RequestMatcher> prefixMatchers;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        for (RequestMatcher matcher : matchers) {
            if (matcher.match(request)) {
                if (authenticate(request, response)) {
                    filterChain.doFilter(request, response);
                }
                return;
            }
        }

        for (RequestMatcher matcher : prefixMatchers) {
            if (matcher.matchStart(request)) {
                if (authenticate(request, response)) {
                    filterChain.doFilter(request, response);
                }
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jwt = request.getHeader(AUTHENTICATION_HEADER);
        if (jwt == null || jwt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        try {
            DecodedJWT decodedJWT = jwtUtil.verify(jwt);
            UUID id = UUID.fromString(decodedJWT.getClaim("id").asString());

            Optional<AccountEntity> optionalAccount = userService.findAccountById(id);
            if (optionalAccount.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return false;
            }
            AccountEntity account = optionalAccount.get();
            UserDetails userDetails = new UserDetailsImpl(account);
            AuthenticationPrinciple principle = new AuthenticationPrinciple(account);

            JwtAuthentication jwtAuthentication = new JwtAuthentication(principle, true, userDetails);
            SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
            return true;
        } catch (JWTVerificationException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
