package com.technokratos.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    public AuthenticationPrinciple getPrincipal() {
        return (AuthenticationPrinciple) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
