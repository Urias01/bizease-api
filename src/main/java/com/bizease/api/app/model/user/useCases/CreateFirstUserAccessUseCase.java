package com.bizease.api.app.model.user.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.AlreadyExistsException;
import com.bizease.api.app.exceptions.CommerceFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.commons.enums.IsActiveEnum;
import com.bizease.api.app.model.user.dto.FirstUserAccessDTO;
import com.bizease.api.app.model.user.dto.UserResponseDTO;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.enums.RoleEnum;
import com.bizease.api.app.model.user.repository.UserRepository;

@Service
public class CreateFirstUserAccessUseCase {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CommerceRepository commerceRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserResponseDTO execute(FirstUserAccessDTO firstUserAccessDTO) {
    this.userRepository.findByEmail(firstUserAccessDTO.getEmail()).ifPresent((user) -> {
      throw new AlreadyExistsException("Usuário");
    });
    
    System.out.println(firstUserAccessDTO.getCnpj());
    this.commerceRepository.findByCnpj(firstUserAccessDTO.getCnpj()).ifPresent((commerce) -> {
      throw new CommerceFoundException("CNPJ já cadastrado");
    });

    Commerce commerce = new Commerce();

    commerce.setName(firstUserAccessDTO.getCommerceName());
    commerce.setCnpj(firstUserAccessDTO.getCnpj());
    commerce.setIsActive(IsActiveEnum.ACTIVE);

    commerce = this.commerceRepository.save(commerce);

    User user = new User();

    String password = passwordEncoder.encode(firstUserAccessDTO.getPassword());
    user.setIsActive(IsActiveEnum.ACTIVE);
    user.setName(firstUserAccessDTO.getName());
    user.setEmail(firstUserAccessDTO.getEmail());
    user.setPassword(password);
    user.setRole(RoleEnum.OWNER);
    user.setCommerce(commerce);

    user = this.userRepository.save(user);

    UserResponseDTO userResponse = new UserResponseDTO(user);
    return userResponse;
  }

}
