package com.bizease.api.app.useCases.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.infrastructure.persistence.jpa.user.UserRepository;
import com.bizease.api.app.infrastructure.security.jwt.IJwtAuthContext;
import com.bizease.api.app.models.entities.User;
import com.bizease.api.app.models.request.UpdateUserRequest;
import com.bizease.api.app.services.users.UpdateUser;

@ExtendWith(MockitoExtension.class)
public class UpdateUserTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private IJwtAuthContext jwtAuthContext;

  @InjectMocks
  private UpdateUser updateUser;

  @Test
  @DisplayName("Should be able to update a user")
  public void shouldBeAbleToUpdateUser() {

    User user = new User();
    user.setId("user-id");
    user.setName("John Doe");

    when(jwtAuthContext.getTenantId()).thenReturn("tenant-id");
    when(userRepository.findByIdAndTenantId("user-id", "tenant-id"))
        .thenReturn(Optional.of(user));

    UpdateUserRequest request = new UpdateUserRequest("Jane Doe", "email@test.com");
    updateUser.execute("user-id", request);

    User updatedUser = userRepository.findByIdAndTenantId("user-id", "tenant-id").orElse(null);

    assertNotNull(updatedUser);
    assertEquals("Jane Doe", updatedUser.getName());
    assertEquals("email@test.com", updatedUser.getEmail());
  }

  @Test
  @DisplayName("Should throw exception when user not found")
  public void shouldThrowExceptionWhenUserNotFound() {
    UpdateUserRequest request = new UpdateUserRequest("Jane Doe", "email@test.com");

    when(jwtAuthContext.getTenantId()).thenReturn("tenant-123");
    when(userRepository.findByIdAndTenantId("user-id", "tenant-123"))
        .thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> updateUser.execute("user-id", request));
  }
}
