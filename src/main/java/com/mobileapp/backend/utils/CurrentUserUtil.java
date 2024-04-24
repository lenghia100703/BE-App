package com.mobileapp.backend.utils;

import org.springframework.stereotype.Component;

@Component
public class CurrentUserUtil {
    private Long userId;


    public void setCurrentUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCurrentUserId() {
        return userId;
    }
}
