package com.mobileapp.backend.constants;

public class JWTConstant {
    public static final String COOKIE_ACCESS_TOKEN = "jwt";
    public static final String COOKIE_REFRESH_TOKEN = "jwt-refresh";

    public static final String HEADER_ACCESS_TOKEN = "jwt";
    public static final String HEADER_REFRESH_TOKEN = "jwt-refresh";

    public static final int ACCESS_TOKEN_EXPIRED = 7 * 24 * 60 * 60; // 1 week

    public static final int REFRESH_TOKEN_EXPIRED = 7 * 24 * 60 * 60 * 4; // 1 month

}
