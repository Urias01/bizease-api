package com.bizease.api.app.model.sales_orders.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.sales_orders.dto.SalesOrdersDTO;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;
import com.bizease.api.app.model.sales_orders.repository.SalesOrdersRepository;

@Service
public class CreateSalesOrderUseCase {

    @Autowired
    private SalesOrdersRepository salesOrdersRepository;
    
    @Autowired
    private CommerceRepository commerceRepository;

    public SalesOrders execute(SalesOrdersDTO salesOrdersDTO) {
        Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(salesOrdersDTO.getCommerceUuid());

        if (!commerceExists.isPresent()) {
            throw new NotFoundException("Comércio");
        }

        SalesOrders salesOrders = new SalesOrders(salesOrdersDTO, commerceExists.get());
        this.salesOrdersRepository.save(salesOrders);

        return salesOrders;
    }
    
}
