package com.bizease.api.app.model.sales_orders.useCases;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.repository.ProductsRepository;
import com.bizease.api.app.model.sales_order_items.dto.SalesOrderItemsDTO;
import com.bizease.api.app.model.sales_order_items.entities.SalesOrderItems;
import com.bizease.api.app.model.sales_order_items.repository.SalesOrderItemsRepository;
import com.bizease.api.app.model.sales_orders.dto.SalesOrdersDTO;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;
import com.bizease.api.app.model.sales_orders.repository.SalesOrdersRepository;

@Service
public class CreateSalesOrderUseCase {

    @Autowired
    private SalesOrdersRepository salesOrdersRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private SalesOrderItemsRepository salesOrderItemsRepository;

    public SalesOrders execute(SalesOrdersDTO salesOrdersDTO) {
        Optional<Commerce> commerceExists = this.commerceRepository.findByUuid(salesOrdersDTO.getCommerceUuid());

        if (!commerceExists.isPresent()) {
            throw new NotFoundException("Comércio");
        }

        SalesOrders salesOrders = new SalesOrders(salesOrdersDTO, commerceExists.get());
        this.salesOrdersRepository.save(salesOrders);

        if (!salesOrdersDTO.getSalesOrdersItems().isEmpty()) {
            List<SalesOrderItemsDTO> salesOrdersItems = salesOrdersDTO.getSalesOrdersItems();

            salesOrdersItems.parallelStream().forEach((salesOrderItem) -> {
                Optional<Products> productExists = this.productsRepository.findByUuid(salesOrderItem.getProductUuid());

                if (!productExists.isPresent()) {
                    throw new NotFoundException("Produto");

                }
                SalesOrderItems salesOrderItems = new SalesOrderItems(salesOrderItem, productExists.get(), salesOrders);
                this.salesOrderItemsRepository.save(salesOrderItems);
            });
        }

        return salesOrders;
    }

}
