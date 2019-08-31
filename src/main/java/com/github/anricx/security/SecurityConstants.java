package com.github.anricx.security;

import org.springframework.http.HttpHeaders;

public final class SecurityConstants {

    // JWT token defaults
    public static final String TOKEN_HEADER = HttpHeaders.AUTHORIZATION;

    public static final String BearerAPIKey = "Bearer <TOKEN>";

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "Bearer";
    public static final String TOKEN_ISSUER = "D.T.'s-API ISSUER";
    public static final String TOKEN_AUDIENCE = "secure-app";

}