package com.bizease.api.app.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(@NotBlank String name, String description, String barcode, @NotNull String price) { }
