package com.bizease.api.app.model.sales_orders.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnnualBuyingAndSellingDTO {

    private String month;
    private BigDecimal buyingTotal;
    private BigDecimal sellingTotal;

}
