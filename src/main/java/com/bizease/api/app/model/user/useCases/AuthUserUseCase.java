package com.bizease.api.app.model.user.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.user.dto.AuthUserRequestDTO;
import com.bizease.api.app.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthUserUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticate(AuthUserRequestDTO authUserRequestDTO) throws AuthenticationException {
        var user = this.userRepository.findByEmail(authUserRequestDTO.getEmail()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Email/senha incorretos");
        });

        var passwordMatches = this.passwordEncoder.matches(authUserRequestDTO.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        var role = user.getRole() != null ? user.getRole().getRole() : "";

        var token = JWT.create().withIssuer("bizease-api")
                .withExpiresAt(expiresIn)
                .withSubject(user.getUuid().toString())
                .withClaim("roles", Arrays.asList(role))
                .sign(algorithm);

        return token;
    }
}
