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
import com.bizease.api.app.models.enums.AccessProfile;
import com.bizease.api.app.models.enums.AccessStatus;
import com.bizease.api.app.models.response.UserResponse;
import com.bizease.api.app.services.users.GetMe;

@ExtendWith(MockitoExtension.class)
public class GetMeTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private IJwtAuthContext jwtAuthContext;
  @InjectMocks
  private GetMe getMe;

  @Test
  @DisplayName("Should be able to return UserResponse for authenticated user")
  public void shouldBeAbleToReturnUserResponseForAuthenticatedUser() {

    when(jwtAuthContext.getUserId()).thenReturn("user-1");
    when(jwtAuthContext.getTenantId()).thenReturn("tenant-123");

    User user = new User();
    user.setId("user-1");
    user.setName("John Doe");
    user.setEmail("john@example.com");
    user.setPassword("password");
    user.setType(AccessProfile.MANAGER);
    user.setStatus(AccessStatus.ACTIVE);
    
    when(userRepository.findByIdAndTenantId("user-1", "tenant-123"))
        .thenReturn(Optional.of(user));

    // Act
    UserResponse result = getMe.execute();

    // Assert
    assertNotNull(result);
    assertEquals(user.getId(), result.id());
    assertEquals(user.getName(), result.name());
    assertEquals(user.getEmail(), result.email());
  }

  @Test
  @DisplayName("Should to be able to throw NotFoundException when user does not exist")
  public void shouldBeAbleToThrowNotFoundExceptionWhenUserDoesNotExist() {
    when(jwtAuthContext.getUserId()).thenReturn("2");
    when(jwtAuthContext.getTenantId()).thenReturn("tenant-123");

    when(userRepository.findByIdAndTenantId("2", "tenant-123"))
        .thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> getMe.execute());

  }

}
