package com.bizease.api.app.ports;

import java.util.List;

import com.bizease.api.app.models.entities.User;

public interface UserQueryRepository {
  
  User findById(String id);
  List<User> findAll();
}
