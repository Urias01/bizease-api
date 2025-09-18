package com.bizease.api.app.model.sales_order_items.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.repository.ProductsRepository;
import com.bizease.api.app.model.sales_order_items.dto.SalesOrderItemsDTO;
import com.bizease.api.app.model.sales_order_items.entities.SalesOrderItems;
import com.bizease.api.app.model.sales_order_items.enums.SalesOrderItemStatus;
import com.bizease.api.app.model.sales_order_items.repository.SalesOrderItemsRepository;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;
import com.bizease.api.app.model.sales_orders.repository.SalesOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateSalesOrderItemsUseCase {

    @Autowired
    private SalesOrderItemsRepository salesOrderItemsRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private SalesOrdersRepository salesOrdersRepository;

    public Long execute(String uuid, SalesOrderItemsDTO salesOrderItemsDTO) {
       SalesOrderItems updatedSalesOrderItem = this.salesOrderItemsRepository.findByUuid(uuid).orElseThrow(() -> {
           throw new NotFoundException("Item de pedido de venda");
       });

        var product = findProduct(salesOrderItemsDTO.getProductUuid());
        var salesOrder = findSalesOrder(salesOrderItemsDTO.getSalesOrderUuid());

        updatedSalesOrderItem.setQuantity(salesOrderItemsDTO.getQuantity());
        updatedSalesOrderItem.setUnitPrice(salesOrderItemsDTO.getUnitPrice());
        updatedSalesOrderItem.setStatus(SalesOrderItemStatus.fromString(salesOrderItemsDTO.getStatus()));
        updatedSalesOrderItem.setProducts(product);
        updatedSalesOrderItem.setSalesOrders(salesOrder);

        updatedSalesOrderItem = this.salesOrderItemsRepository.save(updatedSalesOrderItem);

        return updatedSalesOrderItem.getId();
    }

    private Products findProduct(String uuid) {
        return this.productsRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new NotFoundException("Produto");
        });
    }

    private SalesOrders findSalesOrder(String uuid) {
        return this.salesOrdersRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new NotFoundException("Pedido de venda");
        });
    }
}
