package com.bizease.api.app.infrastructure.persistence.jpa.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bizease.api.app.models.entities.User;
import com.bizease.api.app.ports.UserQueryRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {
  
  private final UserRepository userRepository;

  @Override
  public User findById(String id) {
    return userRepository.findById(id).orElse(null);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }
  
}
