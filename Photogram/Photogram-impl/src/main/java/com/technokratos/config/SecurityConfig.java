package com.technokratos.config;

import com.technokratos.security.JwtUtil;
import com.technokratos.security.RequestMatcher;
import com.technokratos.security.filter.JwtAuthenticationFilter;
import com.technokratos.security.filter.JwtAuthorizationFilter;
import com.technokratos.security.filter.JwtStateFilter;
import com.technokratos.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriterFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, UserService userService) {
        return new JwtAuthenticationFilter(authenticationMatchers(), authenticationPrefixMatchers(), jwtUtil, userService);
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(adminMatchers(), adminPrefixMatchers());
    }

    @Bean
    public JwtStateFilter jwtStateFilter() {
        return new JwtStateFilter(stateMatchers(), statePrefixMatchers());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter,
                                                   JwtAuthorizationFilter jwtAuthorizationFilter,
                                                   JwtStateFilter jwtStateFilter) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.rememberMe().disable();
        httpSecurity.logout().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterAfter(jwtAuthenticationFilter, HeaderWriterFilter.class);
        httpSecurity.addFilterAfter(jwtStateFilter, JwtAuthenticationFilter.class);
        httpSecurity.addFilterAfter(jwtAuthorizationFilter, JwtStateFilter.class);

        return httpSecurity.build();
    }

    private List<RequestMatcher> authenticationMatchers() {
        String apiVersion1 = "/api/v1";
        return List.of(
                new RequestMatcher(apiVersion1 + "/auth/logout",
                        List.of(HttpMethod.PUT)
                )
        );
    }

    private List<RequestMatcher> authenticationPrefixMatchers() {
        String apiVersion1 = "/api/v1";
        return List.of(
                new RequestMatcher(apiVersion1 + "/account",
                        List.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                ),
                new RequestMatcher(apiVersion1 + "/users",
                        List.of(HttpMethod.GET)
                ),
                new RequestMatcher(apiVersion1 + "/files",
                        List.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                ),
                new RequestMatcher(apiVersion1 + "/publications",
                        List.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                )
        );
    }

    private List<RequestMatcher> stateMatchers() {
        return authenticationMatchers();
    }

    private List<RequestMatcher> statePrefixMatchers() {
        return authenticationPrefixMatchers();
    }

    private List<RequestMatcher> adminMatchers() {
        return List.of();
    }

    private List<RequestMatcher> adminPrefixMatchers() {
        return List.of();
    }
}
