package com.bizease.api.app.model.user.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetUserPasswordUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordGenerator passwordGenerator;

    public String resetUserPassword(String email) {
        String randomPassword = passwordGenerator.generateRandomPassword();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuário com o email: " + email));

        String encondedPassword = passwordEncoder.encode(randomPassword);

        user.setPassword(encondedPassword);
        userRepository.save(user);

        return randomPassword;
    }
}
