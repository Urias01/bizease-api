package com.bizease.api.app.model.sales_orders.useCases;

import com.bizease.api.app.model.sales_orders.dto.AnnualBuyingAndSellingDTO;
import com.bizease.api.app.model.sales_orders.repository.SalesOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GetAnnualBuyingAndSellingUseCase {

    @Autowired
    private SalesOrdersRepository salesOrdersRepository;

    public List<AnnualBuyingAndSellingDTO> execute(Long comId) {
        List<Object[]> results = salesOrdersRepository.findAnnualBuyingAndSelling(comId);
        List<AnnualBuyingAndSellingDTO> dtos = new ArrayList<>();

        for (Object[] result: results) {
            String month = (String) result[0];
            BigDecimal buyingTotal = (BigDecimal) result[1];
            BigDecimal sellingTotal = (BigDecimal) result[2];

            AnnualBuyingAndSellingDTO dto = new AnnualBuyingAndSellingDTO(month, buyingTotal, sellingTotal);
            dtos.add(dto);
        }

        return dtos;
    }
}
