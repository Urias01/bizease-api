package com.bizease.api.app.model.commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommerceDTO {

    private String name;
    private String phoneNumber;
    private String postalCode;
    private String address;
    private String addressNumber;
    private String city;
    private String uf;
    private String neighborhood;
}
