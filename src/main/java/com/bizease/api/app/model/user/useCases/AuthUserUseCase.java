package com.bizease.api.app.model.user.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bizease.api.app.model.commons.enums.IsActiveEnum;
import com.bizease.api.app.model.user.dto.AuthUserRequestDTO;
import com.bizease.api.app.model.user.dto.AuthUserResponseDTO;
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

    public AuthUserResponseDTO authenticate(AuthUserRequestDTO authUserRequestDTO) throws AuthenticationException {
        var user = this.userRepository.findByEmail(authUserRequestDTO.getEmail()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Email/senha incorretos");
        });

        if (user.getIsActive() == IsActiveEnum.INACTIVE) {
            throw new AuthenticationException("Usuário inativo. Não é possível autenticar.");
        }

        var passwordMatches = this.passwordEncoder.matches(authUserRequestDTO.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        var role = user.getRole() != null ? user.getRole().getRole() : "";

        var token = JWT.create().withIssuer("bizease-api")
                .withSubject(user.getUuid().toString())
                .withClaim("commerce", user.getCommerce().getUuid())
                .withClaim("roles", Arrays.asList(role))
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        var authUserResponse = AuthUserResponseDTO.builder()
                .accessToken(token)
                .expiresIn(expiresIn.toEpochMilli())
                .build();

        return authUserResponse;
    }
}
