package com.bizease.api.app.model.user.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.exceptions.UserAlreadyExistsException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.user.dto.CreateUserRequestDTO;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.enums.RoleEnum;
import com.bizease.api.app.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser(CreateUserRequestDTO createUserRequestDTO) {
        Optional<User> verifyUser = this.userRepository.findByEmail(createUserRequestDTO.getEmail());

        if(verifyUser.isPresent()) {
            throw new UserAlreadyExistsException("Usuário já existente com este email!");
        } else {
            Commerce commerce = commerceRepository.findById(createUserRequestDTO.getCommerceId())
                    .orElseThrow(() -> new NotFoundException("Comércio"));

            RoleEnum role = RoleEnum.fromString(createUserRequestDTO.getRole());

            User newUser = new User();
            newUser.setName(createUserRequestDTO.getName());
            newUser.setEmail(createUserRequestDTO.getEmail());
            newUser.setPassword(passwordEncoder.encode(createUserRequestDTO.getPassword()));
            newUser.setCommerce(commerce);
            newUser.setRole(role);

            return userRepository.save(newUser);
        }
    }
}
