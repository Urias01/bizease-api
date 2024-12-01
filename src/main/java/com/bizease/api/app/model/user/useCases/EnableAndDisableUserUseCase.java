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
public class EnableAndDisableUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public void execute(UUID userUuid) {
        User user = userRepository.findByUuid(userUuid).orElseThrow(() -> {
            throw new NotFoundException("Usuário");
        });

        IsActiveEnum newStatus;

        if (user.getIsActive() == IsActiveEnum.ACTIVE) {
            newStatus = IsActiveEnum.INACTIVE;
        } else {
            newStatus = IsActiveEnum.ACTIVE;
        }

        user.setIsActive(newStatus);

        userRepository.save(user);
    }

}
