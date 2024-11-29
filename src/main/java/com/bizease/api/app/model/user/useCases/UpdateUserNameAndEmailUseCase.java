package com.bizease.api.app.model.user.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bizease.api.app.exceptions.InvalidInputException;
import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.user.dto.UpdateUserNameAndEmailDTO;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.repository.UserRepository;

@Service
public class UpdateUserNameAndEmailUseCase {
    
    @Autowired
    private UserRepository userRepository;

    public String execute(UUID uuid, UpdateUserNameAndEmailDTO updateUserNameAndEmailDTO) {
            
        validateInput(updateUserNameAndEmailDTO);

        Optional<User> verifyUser = this.userRepository.findByUuid(uuid);

        if (verifyUser.isPresent()) {
            User user = verifyUser.get();
            user.setName(updateUserNameAndEmailDTO.getName());
            user.setEmail(updateUserNameAndEmailDTO.getEmail());

            this.userRepository.save(user);
            return "Nome e E-mail de usuário alterado com sucesso.";
        } else {
            throw new NotFoundException("Usuário");
        }
    }

    private void validateInput(UpdateUserNameAndEmailDTO dto) {
        if (dto == null) {
            throw new InvalidInputException("Os dados para atualização não podem estar vazios.");
        }

        if (!StringUtils.hasText(dto.getName())) {
            throw new InvalidInputException("O nome não pode ser nulo ou vazio.");
        }

        if (!StringUtils.hasText(dto.getEmail())) {
            throw new InvalidInputException("O e-mail não pode ser nulo ou vazio.");
        }

        if (!dto.getEmail().contains("@")) {
            throw new InvalidInputException("O e-mail fornecido é inválido.");
        }
    }
}
