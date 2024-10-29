package com.bizease.api.app.model.sales_orders.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;
import com.bizease.api.app.model.sales_orders.repository.SalesOrdersRepository;

@Service
public class DeleteSalesOrderUseCase {
    
    @Autowired
    private SalesOrdersRepository salesOrdersRepository;

    public void execute(String uuid) {
        Optional<SalesOrders> salesOrderExists = salesOrdersRepository.findByUuid(uuid);

        if (!salesOrderExists.isPresent()) {
            throw new NotFoundException("Pedido de Venda");
        }

        SalesOrders salesOrders = salesOrderExists.get();

        this.salesOrdersRepository.delete(salesOrders);
    }

}
