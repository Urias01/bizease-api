package com.bizease.api.app.useCases.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.AlreadyExistException;
import com.bizease.api.app.exceptions.BadRequestException;
import com.bizease.api.app.mappers.UserMapper;
import com.bizease.api.app.models.Tenant;
import com.bizease.api.app.models.User;
import com.bizease.api.app.models.request.UserRequest;
import com.bizease.api.app.repositories.TenantRepository;
import com.bizease.api.app.repositories.UserRepository;
import com.bizease.api.app.security.jwt.IJwtAuthContext;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateUser {

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;
  private final TenantRepository tenantRepository;
  private final IJwtAuthContext jwtAuthContext;

  public String execute(UserRequest request) {
    String tenantId = jwtAuthContext.getTenantId();
    return create(request, tenantId);
  }

  public String execute(UserRequest request, String tenantId) {
    return create(request, tenantId);
  }

  private String create(UserRequest request, String tenantId) {
    User user = UserMapper.toEntity(request);

    Tenant tenant = tenantRepository.findById(tenantId)
        .orElseThrow(() -> new BadRequestException("Invalid tenant ID"));
    user.setTenant(tenant);
    
    if (userRepository.findByEmailAndTenantId(user.getEmail(), tenantId).isPresent()) {
      throw new AlreadyExistException("User");
    }

    if (!request.password().contentEquals(request.confirmPassword())) {
      throw new BadRequestException("Password not match");
    }

    String hashedPassword = passwordEncoder.encode(request.password());
    user.setPassword(hashedPassword);

    userRepository.save(user);

    return user.getId();
  }

}
