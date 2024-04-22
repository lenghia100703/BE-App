package com.mobileapp.backend.utils;

import com.mobileapp.backend.configs.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {
    public static Long getCurrentUserId() {
        CustomAuthentication principal = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return principal.getId();
    }
}
