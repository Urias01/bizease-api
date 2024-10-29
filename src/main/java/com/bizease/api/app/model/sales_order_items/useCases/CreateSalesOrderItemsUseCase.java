package com.bizease.api.app.model.sales_order_items.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.repository.ProductsRepository;
import com.bizease.api.app.model.sales_order_items.dto.SalesOrderItemsDTO;
import com.bizease.api.app.model.sales_order_items.entities.SalesOrderItems;
import com.bizease.api.app.model.sales_order_items.repository.SalesOrderItemsRepository;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;
import com.bizease.api.app.model.sales_orders.repository.SalesOrdersRepository;

@Service
public class CreateSalesOrderItemsUseCase {

    @Autowired
    private SalesOrderItemsRepository salesOrderItemsRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private SalesOrdersRepository salesOrdersRepository;

    public SalesOrderItems execute(SalesOrderItemsDTO salesOrderItemsDTO) {
        
        Optional<Products> productExists = this.productsRepository.findByUuid(salesOrderItemsDTO.getProductUuid());
        if (!productExists.isPresent()) {
            throw new NotFoundException("Produto");
        }

        Optional<SalesOrders> salesOrderExists = this.salesOrdersRepository.findByUuid(salesOrderItemsDTO.getSalesOrderUuid());
        if (!salesOrderExists.isPresent()) {
            throw new NotFoundException("Pedido de venda");
        }

        SalesOrderItems salesOrderItems = new SalesOrderItems(salesOrderItemsDTO, productExists.get(), salesOrderExists.get());
        this.salesOrderItemsRepository.save(salesOrderItems);

        return salesOrderItems;
    }
    
}
