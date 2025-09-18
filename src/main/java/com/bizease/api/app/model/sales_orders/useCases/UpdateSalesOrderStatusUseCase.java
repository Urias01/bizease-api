package com.bizease.api.app.model.sales_orders.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;
import com.bizease.api.app.model.sales_orders.enums.SalesOrderStatus;
import com.bizease.api.app.model.sales_orders.repository.SalesOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateSalesOrderStatusUseCase {

    @Autowired
    private SalesOrdersRepository salesOrdersRepository;

    public Long execute(UUID uuid, SalesOrderStatus newStatus) {
        SalesOrders salesOrders = salesOrdersRepository.findByUuid(String.valueOf(uuid)).orElseThrow(() -> {
            throw new NotFoundException("Pedido de venda");
        });

        SalesOrderStatus currentStatus = salesOrders.getStatus();
        SalesOrderStatus updatedStatus = currentStatus.transitionTo(newStatus);

        salesOrders.setStatus(updatedStatus);

        salesOrders = salesOrdersRepository.save(salesOrders);
        return salesOrders.getId();
    }
}
