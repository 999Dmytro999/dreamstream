package com.dreamstream.auth.dto;

import com.dreamstream.users.UserRole;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        UserRole role,
        Instant createdAt,
        Instant updatedAt
) {
}
