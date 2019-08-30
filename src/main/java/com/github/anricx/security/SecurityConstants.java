package com.github.anricx.security;

import org.springframework.http.HttpHeaders;

public final class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/api/authenticate";

    // Signing key for HS512 algorithm
    // You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
    public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

    // JWT token defaults
    public static final String TOKEN_HEADER = HttpHeaders.AUTHORIZATION;

    public static final String BearerAPIKey = "Bearer <JWT>";

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "Bearer";
    public static final String TOKEN_ISSUER = "D.T.'s-API ISSUER";
    public static final String TOKEN_AUDIENCE = "secure-app";

}