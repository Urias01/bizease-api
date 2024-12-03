package com.bizease.api.app.model.sales_orders.useCases;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.sales_order_items.entities.SalesOrderItems;
import com.bizease.api.app.model.sales_order_items.repository.SalesOrderItemsRepository;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;
import com.bizease.api.app.model.sales_orders.repository.SalesOrdersRepository;

@Service
public class GetSalesOrderByUuid {

  @Autowired
  private SalesOrdersRepository salesOrdersRepository;

  @Autowired
  private SalesOrderItemsRepository salesOrderItemsRepository;

  public SalesOrders execute(String uuid) {

    Optional<SalesOrders> salesOrdersExists = this.salesOrdersRepository.findByUuid(uuid);

    if (!salesOrdersExists.isPresent()) {
      throw new NotFoundException("Pedido de venda");
    }

    SalesOrders model = salesOrdersExists.get();

    List<SalesOrderItems> salesOrderItems = this.salesOrderItemsRepository.findBySalesOrdersId(model.getId());

    salesOrderItems.stream().forEach((salesItems) -> {
      salesItems.setSalesOrders(null);
    });

    model.setSalesOrderItems(salesOrderItems);

    return model;
  }
}
