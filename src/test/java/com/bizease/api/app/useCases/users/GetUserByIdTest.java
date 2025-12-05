package com.bizease.api.app.useCases.users;

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

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.infrastructure.persistence.jpa.user.UserRepository;
import com.bizease.api.app.infrastructure.security.jwt.IJwtAuthContext;
import com.bizease.api.app.mappers.UserMapper;
import com.bizease.api.app.models.entities.User;
import com.bizease.api.app.models.response.UserResponse;
import com.bizease.api.app.services.users.GetUserById;

@ExtendWith(MockitoExtension.class)
public class GetUserByIdTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private IJwtAuthContext jwtAuthContext;

  @InjectMocks
  private GetUserById getUserById;

  @Test
  @DisplayName("Should be able to get user by ID")
  public void shouldBeAbleToGetUserById() {
    String id = "user-123";
    User user = new User();
    user.setId(id);
    user.setName("John Doe");

    when(jwtAuthContext.getTenantId()).thenReturn("tenant-123");
    when(userRepository.findByIdAndTenantId(id, "tenant-123"))
        .thenReturn(Optional.of(user));

    var response = getUserById.execute(id);

    UserResponse userResponse = UserMapper.toResponse(user);

    assertEquals(userResponse, response);
  }

  @Test
  @DisplayName("Should throw exception when user not found")
  public void shouldThrowExceptionWhenUserNotFound() {
    String id = "user-123";

    when(jwtAuthContext.getTenantId()).thenReturn("tenant-123");
    when(userRepository.findByIdAndTenantId(id, "tenant-123"))
        .thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> getUserById.execute(id));
  }
}
