package com.dreamstream.security;

import com.dreamstream.users.UserRole;

import java.util.UUID;

public record CurrentUser(UUID id, String email, UserRole role) {
}
