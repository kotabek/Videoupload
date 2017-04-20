package com.video.utils;

import com.video.spring.to.AuthDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by kotabek on 4/20/17.
 */
public final class SecurityContextUtils {
    private static SecurityContext getContext() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null
            || context.getAuthentication() == null
            || !context.getAuthentication().isAuthenticated()) {
            return null;
        }
        return context;
    }

    public static Long getMemberId() {
        SecurityContext context = getContext();
        if (context == null) {
            return null;
        }

        Authentication authentication = context.getAuthentication();
        if (authentication != null
            && authentication.getDetails() != null
            && authentication.getDetails() instanceof AuthDetails) {
            return ((AuthDetails) authentication.getDetails()).getMemberId();
        }

        return null;
    }

}
