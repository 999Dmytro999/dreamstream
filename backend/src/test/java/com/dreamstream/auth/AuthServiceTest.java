package com.dreamstream.auth;

import com.dreamstream.auth.dto.LoginRequest;
import com.dreamstream.auth.dto.RegisterRequest;
import com.dreamstream.common.exception.UnauthorizedException;
import com.dreamstream.security.JwtService;
import com.dreamstream.users.UserEntity;
import com.dreamstream.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerShouldHashPassword() {
        RegisterRequest request = new RegisterRequest("Jane", "Doe", "jane@demo.com", "password123");
        when(userRepository.existsByEmailIgnoreCase("jane@demo.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hashed");

        UserEntity saved = new UserEntity();
        saved.setId(UUID.randomUUID());
        saved.setFirstName("Jane");
        saved.setLastName("Doe");
        saved.setEmail("jane@demo.com");
        saved.setPasswordHash("hashed");

        when(userRepository.save(any(UserEntity.class))).thenReturn(saved);
        when(jwtService.generateToken(saved)).thenReturn("token");

        var response = authService.register(request);

        assertEquals("token", response.accessToken());
        assertEquals("jane@demo.com", response.user().email());
    }

    @Test
    void loginShouldFailOnBadPassword() {
        UserEntity user = new UserEntity();
        user.setEmail("jane@demo.com");
        user.setPasswordHash("hashed");

        when(userRepository.findByEmailIgnoreCase("jane@demo.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "hashed")).thenReturn(false);

        assertThrows(UnauthorizedException.class,
                () -> authService.login(new LoginRequest("jane@demo.com", "wrongpass")));
    }
}
