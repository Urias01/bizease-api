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

import com.bizease.api.app.repositories.UserRepository;
import com.bizease.api.app.exceptions.AuthenticationException;
import com.bizease.api.app.models.Tenant;
import com.bizease.api.app.models.User;
import com.bizease.api.app.models.request.AuthRequest;
import com.bizease.api.app.models.response.AuthResponse;
import com.bizease.api.app.repositories.TenantRepository;
import com.bizease.api.app.security.jwt.JwtService;

@ExtendWith(MockitoExtension.class)
public class SignInTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private TenantRepository tenantRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private JwtService jwtService;

  @InjectMocks
  private SignIn signIn;


  @Test
  @DisplayName("Should sign in successfully with valid credentials")
  void shouldSignInSuccessfullyWithValidCredentials() {
    String email = "john@doe.com";
    String password = "password123";
    Tenant tenant = new Tenant();
    tenant.setId("tenant-123");

    User user = new User();
    user.setId("user-123");
    user.setPassword("hashedPassword");

    when(tenantRepository.findBySlug("tenant_slug")).thenReturn(Optional.of(tenant));
    when(userRepository.findByEmailAndTenantId(email, tenant.getId())).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(password, "hashedPassword")).thenReturn(true);

    AuthRequest request = new AuthRequest(email, password, "tenant_slug");
    // Call the method under test

    when(jwtService.generateToken("user-123", "tenant-123")).thenReturn("expectedToken");

    AuthResponse response = signIn.execute(request);

    assertEquals("expectedToken", response.token());
  }

  @Test
  @DisplayName("Should throw AuthenticationException for invalid password")
  void shouldThrowAuthenticationExceptionForInvalidPassword() {
    String email = "john@doe.com";
    String password = "WrongPassword";
    Tenant tenant = new Tenant();
    tenant.setId("tenant-123");

    User user = new User();
    user.setId("user-123");
    user.setPassword("hashedPassword");

    when(tenantRepository.findBySlug("tenant_slug")).thenReturn(Optional.of(tenant));
    when(userRepository.findByEmailAndTenantId(email, tenant.getId())).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(password, "hashedPassword")).thenReturn(false);

    AuthRequest request = new AuthRequest(email, password, "tenant_slug");

    AuthenticationException exception = assertThrows(AuthenticationException.class, () -> signIn.execute(request));
    assertEquals("Invalid email or password", exception.getMessage());
  }
}
