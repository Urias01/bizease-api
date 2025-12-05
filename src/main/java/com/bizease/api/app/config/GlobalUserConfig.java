package com.bizease.api.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bizease.api.app.infrastructure.persistence.jpa.user.UserRepository;
import com.bizease.api.app.models.entities.User;
import com.bizease.api.app.models.enums.AccessProfile;

import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;

@Configuration
public class GlobalUserConfig {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AppProperties appProperties;

  @PostConstruct
  public void createGlobalUser() {
    String email = appProperties.getGlobalUserEmail();
    String password = appProperties.getGlobalUserPassword();

    if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
      System.out.println("Variáveis de ambiente do usuário global não definidas ou vazias. Ignorando criação.");
      return;
    }

    if (userRepository.findByEmail(email).isEmpty()) {
      User globalUser = new User();
      globalUser.setEmail(email);
      globalUser.setName("Admin Global");
      globalUser.setPassword(passwordEncoder.encode(password));
      globalUser.setType(AccessProfile.ADMIN);
      userRepository.save(globalUser);

      System.out.println("Usuário global criado!");
    } else {
      System.out.println("Usuário global já existe.");
    }
  }
}
