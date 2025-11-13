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
import com.bizease.api.app.models.User;
import com.bizease.api.app.models.enums.AccessStatus;
import com.bizease.api.app.models.request.UpdateUserRequest;
import com.bizease.api.app.repositories.UserRepository;
import com.bizease.api.app.security.jwt.IJwtAuthContext;

@ExtendWith(MockitoExtension.class)
public class DeleteUserTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private IJwtAuthContext jwtAuthContext;

  @InjectMocks
  private DeleteUser deleteUser;

  @Test
  @DisplayName("Should delete user successfully")
  public void shouldDeleteUserSuccessfully() {
    User user = new User();
    user.setId("user-id");
    user.setStatus(AccessStatus.ACTIVE);

    when(jwtAuthContext.getTenantId()).thenReturn("tenant-id");
    when(userRepository.findByIdAndTenantId("user-id", "tenant-id")).thenReturn(Optional.of(user));
    when(userRepository.save(user)).thenReturn(user);
    deleteUser.execute("user-id");

    assertEquals(AccessStatus.INACTIVE, user.getStatus());
  }

  @Test
  @DisplayName("Should throw exception when user not found")
  public void shouldThrowExceptionWhenUserNotFound() {
    when(jwtAuthContext.getTenantId()).thenReturn("tenant-123");
    when(userRepository.findByIdAndTenantId("user-id", "tenant-123"))
        .thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> deleteUser.execute("user-id"));
  }
}
