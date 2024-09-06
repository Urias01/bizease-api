package com.bizease.api.app.model.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserResponseDTO {

    private String acess_token;
    private Long expires_in;
}
