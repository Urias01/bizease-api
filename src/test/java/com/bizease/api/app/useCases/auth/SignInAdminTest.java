package com.bizease.api.app.useCases.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bizease.api.app.exceptions.AuthenticationException;
import com.bizease.api.app.infrastructure.persistence.jpa.tenant.TenantRepository;
import com.bizease.api.app.infrastructure.persistence.jpa.user.UserRepository;
import com.bizease.api.app.infrastructure.security.jwt.JwtService;
import com.bizease.api.app.models.entities.Tenant;
import com.bizease.api.app.models.entities.User;
import com.bizease.api.app.models.enums.AccessStatus;
import com.bizease.api.app.models.request.AuthRequest;
import com.bizease.api.app.models.response.AuthResponse;
import com.bizease.api.app.services.auth.SignInAdmin;

@ExtendWith(MockitoExtension.class)
public class SignInAdminTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private JwtService jwtService;

  @InjectMocks
  private SignInAdmin signInAdmin;

  @Test
  @DisplayName("Should sign in successfully with valid credentials")
  void shouldSignInSuccessfullyWithValidCredentials() {
    String email = "john@doe.com";
    String password = "password123";

    User user = new User();
    user.setId("user-123");
    user.setPassword("hashedPassword");
    user.setStatus(AccessStatus.ACTIVE);

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(password, "hashedPassword")).thenReturn(true);

    AuthRequest request = new AuthRequest(email, password,  null);
    // Call the method under test

    when(jwtService.generateToken("user-123", null)).thenReturn("expectedToken");

    AuthResponse response = signInAdmin.execute(request);

    assertEquals("expectedToken", response.token());
  }

  @Test
  @DisplayName("Should throw AuthenticationException for invalid password")
  void shouldThrowAuthenticationExceptionForInvalidPassword() {
    String email = "john@doe.com";
    String password = "WrongPassword";

    User user = new User();
    user.setId("user-123");
    user.setPassword("hashedPassword");
    user.setStatus(AccessStatus.ACTIVE);

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(password, "hashedPassword")).thenReturn(false);

    AuthRequest request = new AuthRequest(email, password, null);

    AuthenticationException exception = assertThrows(AuthenticationException.class, () -> signInAdmin.execute(request));
    assertEquals("Invalid email or password", exception.getMessage());
  }

  @Test
  @DisplayName("Should throw AuthenticationException for inactive user")
  void shouldThrowAuthenticationExceptionForInactiveUser() {
    String email = "john@doe.com";
    String password = "password123";

    User user = new User();
    user.setId("user-123");
    user.setPassword("hashedPassword");
    user.setStatus(AccessStatus.INACTIVE);

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(password, "hashedPassword")).thenReturn(true);

    AuthRequest request = new AuthRequest(email, password, null);

    AuthenticationException exception = assertThrows(AuthenticationException.class, () -> signInAdmin.execute(request));
    assertEquals("User is inactive", exception.getMessage());
  }
}
