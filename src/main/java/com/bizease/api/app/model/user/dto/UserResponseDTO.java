package com.bizease.api.app.model.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(Long id, UUID uuid, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
