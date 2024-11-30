package com.bizease.api.app.model.user.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.user.dto.UserResponseDTO;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetUserByUuidUseCase {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommerceRepository commerceRepository;

    public UserResponseDTO getUserByUuid(UUID uuid, String commerceUuid) {
        Optional<User> user = userRepository.findByUuid(uuid);
        Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(commerceUuid);

        if (!commerceExists.isPresent()) {
            throw new NotFoundException("Comércio");
        }

        if (user.isPresent()) {
            User foundUser = user.get();
            return new UserResponseDTO(foundUser);
        } else {
            throw new NotFoundException("Usuário");
        }
    }
}
