package com.bizease.api.app.model.user.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commons.enums.IsActiveEnum;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public void deleteUser(String uuid) {
        Optional<User> verifyUser = userRepository.findByUuid(UUID.fromString(uuid));

        if(verifyUser.isPresent()) {
            User user = verifyUser.get();
            user.setIsActive(IsActiveEnum.INACTIVE);
            userRepository.save(user);
        } else {
            throw new NotFoundException("Usuário");
        }
    }
}
