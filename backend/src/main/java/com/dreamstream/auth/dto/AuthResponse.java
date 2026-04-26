package com.dreamstream.auth.dto;

public record AuthResponse(
        String accessToken,
        String tokenType,
        UserResponse user
) {
}
