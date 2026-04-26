package com.dreamstream.auth;

import com.dreamstream.auth.dto.AuthResponse;
import com.dreamstream.auth.dto.LoginRequest;
import com.dreamstream.auth.dto.RegisterRequest;
import com.dreamstream.auth.dto.UserResponse;
import com.dreamstream.common.exception.ConflictException;
import com.dreamstream.common.exception.UnauthorizedException;
import com.dreamstream.security.CurrentUser;
import com.dreamstream.security.JwtService;
import com.dreamstream.users.UserEntity;
import com.dreamstream.users.UserRepository;
import com.dreamstream.users.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new ConflictException("Email is already registered");
        }

        UserEntity user = new UserEntity();
        user.setFirstName(request.firstName().trim());
        user.setLastName(request.lastName().trim());
        user.setEmail(normalizedEmail);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(UserRole.USER);

        UserEntity saved = userRepository.save(user);
        return toAuthResponse(saved);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        UserEntity user = userRepository.findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        return toAuthResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse currentUser(CurrentUser currentUser) {
        UserEntity user = userRepository.findById(currentUser.id())
                .orElseThrow(() -> new UnauthorizedException("Current user not found"));
        return toUserResponse(user);
    }

    private AuthResponse toAuthResponse(UserEntity user) {
        return new AuthResponse(jwtService.generateToken(user), "Bearer", toUserResponse(user));
    }

    private UserResponse toUserResponse(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
