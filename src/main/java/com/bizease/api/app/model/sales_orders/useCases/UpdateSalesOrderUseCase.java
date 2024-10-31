package com.bizease.api.app.model.sales_orders.useCases;

import java.util.Optional;

import com.bizease.api.app.model.sales_orders.enums.SalesOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.sales_orders.dto.SalesOrdersDTO;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;
import com.bizease.api.app.model.sales_orders.repository.SalesOrdersRepository;

@Service
public class UpdateSalesOrderUseCase {

    @Autowired
    private SalesOrdersRepository salesOrdersRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    public SalesOrders execute(SalesOrdersDTO salesOrdersDTO, String uuid) {
        
        Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(salesOrdersDTO.getCommerceUuid());

        if (!commerceExists.isPresent()) {
            throw new NotFoundException("Comércio");
        }

        Optional<SalesOrders> salesOrderExists = this.salesOrdersRepository.findByUuid(uuid);

        if (!salesOrderExists.isPresent()) {
            throw new NotFoundException("Pedido de compra");
        }

        SalesOrders salesOrders = salesOrderExists.get();

        salesOrders.setStatus(SalesOrderStatus.fromString(salesOrdersDTO.getStatus()));
        salesOrders.setOrderDate(salesOrdersDTO.getOrderDate());
        salesOrders.setDeliveryDate(salesOrdersDTO.getDeliveryDate());

        salesOrders = this.salesOrdersRepository.save(salesOrders);

        return salesOrders;
    }
    
}
