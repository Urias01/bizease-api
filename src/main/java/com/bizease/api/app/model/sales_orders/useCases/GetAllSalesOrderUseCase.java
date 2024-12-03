package com.bizease.api.app.model.sales_orders.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.sales_order_items.dto.SalesOrderItemsDTO;
import com.bizease.api.app.model.sales_order_items.entities.SalesOrderItems;
import com.bizease.api.app.model.sales_order_items.repository.SalesOrderItemsRepository;
import com.bizease.api.app.model.sales_orders.dto.SalesOrdersDTO;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;
import com.bizease.api.app.model.sales_orders.filter.SalesOrderFilter;
import com.bizease.api.app.model.sales_orders.repository.SalesOrdersRepository;

import static com.bizease.api.app.model.sales_orders.specifications.SalesOrderSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllSalesOrderUseCase {

  @Autowired
  private SalesOrdersRepository salesOrdersRepository;

  @Autowired
  private SalesOrderItemsRepository salesOrderItemsRepository;

  public PageReturn<List<SalesOrders>> execute(SalesOrderFilter filter) {

    Specification<SalesOrders> specification = where(
        commerceUuidEquals(filter.getCommerceUuid()).and(idEquals(filter.getId())));

    Direction direction = Direction.valueOf(filter.getDirection().toUpperCase());

    PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize(), direction, filter.getField());

    Page<SalesOrders> model = this.salesOrdersRepository.findAll(specification, pageRequest);

    List<SalesOrders> responses = model.getContent();

    responses.stream().forEach((response) -> {
      List<SalesOrderItems> salesOrderItems = this.salesOrderItemsRepository.findBySalesOrdersId(response.getId());

      salesOrderItems.stream().forEach((salesItems) -> {
        salesItems.setSalesOrders(null);
      });
      
      response.setSalesOrderItems(salesOrderItems);
    });

    return new PageReturn<List<SalesOrders>>(responses, model.getTotalElements(), pageRequest.getPageNumber(),
        pageRequest.getPageSize());
  }
}
