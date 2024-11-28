package com.bizease.api.app.model.movements.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementDTO {

    private String type;
    private Integer quantity;
    private String origin;
    private String destination;
    private LocalDate movementDate;
    private String observation;
    private Long userId;
    private Long productId;
    private Long commerceId;
}
