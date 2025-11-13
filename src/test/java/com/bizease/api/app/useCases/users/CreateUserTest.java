package com.bizease.api.app.useCases.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bizease.api.app.exceptions.AlreadyExistException;
import com.bizease.api.app.exceptions.BadRequestException;
import com.bizease.api.app.models.Tenant;
import com.bizease.api.app.models.User;
import com.bizease.api.app.models.request.UserRequest;
import com.bizease.api.app.repositories.TenantRepository;
import com.bizease.api.app.repositories.UserRepository;
import com.bizease.api.app.security.jwt.IJwtAuthContext;

@ExtendWith(MockitoExtension.class)
public class CreateUserTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private TenantRepository tenantRepository;
  @Mock
  private IJwtAuthContext jwtAuthContext;
  @Mock
  private PasswordEncoder passwordEncoder;
  @InjectMocks
  private CreateUser createUser;

  @Test
  @DisplayName("Should be able to create a new user")
  public void shouldBeAbleToCreateNewUser() {
    when(jwtAuthContext.getTenantId()).thenReturn("tenant-123");

    UserRequest request = new UserRequest(
        "Jane Doe",
        "jane@doe.com",
        "password123", "password123", null, null, null);

    Tenant tenant = new Tenant();
    tenant.setId("tenant-123");

    when(tenantRepository.findById("tenant-123")).thenReturn(Optional.of(tenant));
    when(userRepository.findByEmailAndTenantId(request.email(), tenant.getId())).thenReturn(Optional.empty());
    when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

    when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
      User savedUser = invocation.getArgument(0);
      savedUser.setId("user-1");
      return savedUser;
    });

    String userId = createUser.execute(request);

    assertEquals("user-1", userId);
  }

  @Test
  @DisplayName("Should be able to create a new user with specified tenant ID")
  public void shouldBeAbleToCreateNewUserWithSpecifiedTenantId() {
    UserRequest request = new UserRequest(
        "John Doe",
        "john@doe.com",
        "password123", "password123", null, null, null);

    Tenant tenant = new Tenant();
    tenant.setId("tenant-123");

    when(tenantRepository.findById("tenant-123")).thenReturn(Optional.of(tenant));
    when(userRepository.findByEmailAndTenantId(request.email(), tenant.getId())).thenReturn(Optional.empty());
    when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

    when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
      User savedUser = invocation.getArgument(0);
      savedUser.setId("user-2");
      return savedUser;
    });

    String userId = createUser.execute(request, tenant.getId());

    assertEquals("user-2", userId);
  }

  @Test
  @DisplayName("Should be able to return BadRequestException when passwords do not match")
  public void shouldBeAbleToReturnBadRequestExceptionWhenPasswordsDoNotMatch() {
    when(jwtAuthContext.getTenantId()).thenReturn("tenant-123");

    UserRequest request = new UserRequest(
        "Jane Doe",
        "jane@doe.com",
        "password123", "differentPassword", null, null, null);

    Tenant tenant = new Tenant();
    tenant.setId("tenant-123");

    when(tenantRepository.findById("tenant-123")).thenReturn(Optional.of(tenant));
    when(userRepository.findByEmailAndTenantId(request.email(), tenant.getId())).thenReturn(Optional.empty());

    BadRequestException exception = assertThrows(
        BadRequestException.class,
        () -> createUser.execute(request));

    assertEquals("Password not match", exception.getMessage());
  }

  @Test
  @DisplayName("Should be able to return AlreadyExistException when user email already exists")
  public void shouldBeAbleToReturnAlreadyExistExceptionWhenUserEmailAlreadyExists() {
    when(jwtAuthContext.getTenantId()).thenReturn("tenant-123");

    UserRequest request = new UserRequest(
        "Jane Doe",
        "jane@doe.com",
        "password123", "differentPassword", null, null, null);

    Tenant tenant = new Tenant();
    tenant.setId("tenant-123");

    when(tenantRepository.findById("tenant-123")).thenReturn(Optional.of(tenant));
    User existingUser = new User();
    existingUser.setEmail(request.email());
    existingUser.setTenant(tenant);

    when(userRepository.findByEmailAndTenantId(request.email(), tenant.getId()))
        .thenReturn(Optional.of(existingUser));

    AlreadyExistException exception = assertThrows(
        AlreadyExistException.class,
        () -> createUser.execute(request));

    assertEquals("User already exists.", exception.getMessage());
  }

  @Test
  @DisplayName("Should throw BadRequestException when tenant ID is invalid")
  public void shouldThrowBadRequestExceptionWhenTenantIdIsInvalid() {
    when(jwtAuthContext.getTenantId()).thenReturn("invalid-tenant-id");

    UserRequest request = new UserRequest(
        "Jane Doe",
        "jane@doe.com",
        "password123", "password123", null, null, null);

    when(tenantRepository.findById("invalid-tenant-id")).thenReturn(Optional.empty());

    BadRequestException exception = assertThrows(
        BadRequestException.class,
        () -> createUser.execute(request));

    assertEquals("Invalid tenant ID", exception.getMessage());
  }

}
