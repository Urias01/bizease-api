package com.bizease.api.app.model.user.useCases;

import com.bizease.api.app.exceptions.InvalidPasswordException;
import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.user.dto.PasswordChangeDTO;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChangePasswordUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void changePassword(UUID userUuid, PasswordChangeDTO passwordChangeDTO) {
        User user = userRepository.findByUuid(userUuid).orElseThrow(() -> {
            throw new NotFoundException("Usuário");
        });

        if (!passwordEncoder.matches((passwordChangeDTO.getCurrentPassword()), user.getPassword())) {
            throw new InvalidPasswordException("Senha atual incorreta!");
        }

        if (passwordEncoder.matches(passwordChangeDTO.getNewPassword(), user.getPassword())) {
            throw new InvalidPasswordException("A nova senha não pode ser igual à senha atual!");
        }

        user.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        userRepository.save(user);
    }
}
