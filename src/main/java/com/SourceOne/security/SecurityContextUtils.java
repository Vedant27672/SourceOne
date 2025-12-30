package com.SourceOne.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityContextUtils {

    private SecurityContextUtils() {
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getCurrentUsername() {
        Authentication auth = getAuthentication();
        return auth != null ? auth.getName() : null;
    }

    public static UserDetails getCurrentUserDetails() {
        Authentication auth = getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            return (UserDetails) auth.getPrincipal();
        }
        return null;
    }
}
