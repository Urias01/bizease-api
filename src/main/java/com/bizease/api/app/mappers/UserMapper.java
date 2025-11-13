package com.bizease.api.app.mappers;

import com.bizease.api.app.models.User;
import com.bizease.api.app.models.enums.AccessStatus;
import com.bizease.api.app.models.request.UserRequest;
import com.bizease.api.app.models.response.UserResponse;

public class UserMapper {
  
  public static User toEntity(UserRequest request) {
    
    User user = new User();
    user.setName(request.name());
    user.setEmail(request.email());
    user.setStatus(AccessStatus.ACTIVE);
    user.setType(request.type());

    return user;
  }

  public static UserResponse toResponse(User user) {
    return new UserResponse(
        user.getId(),
        user.getEmail(),
        user.getName()
    );
  }
}
