package com.bizease.api.app.model.purchase_orders.useCases;

import com.bizease.api.app.model.commons.PageReturn;
import com.bizease.api.app.model.purchase_order_items.entities.PurchaseOrderItem;
import com.bizease.api.app.model.purchase_order_items.repository.PurchaseOrdemItemRepository;
import com.bizease.api.app.model.purchase_orders.entities.PurchaseOrders;
import com.bizease.api.app.model.purchase_orders.filter.PurchaseOrderFilter;
import com.bizease.api.app.model.purchase_orders.repository.PurchaseOrdersRepository;
import com.bizease.api.app.model.sales_order_items.entities.SalesOrderItems;
import com.bizease.api.app.model.sales_orders.entities.SalesOrders;
import com.bizease.api.app.model.sales_orders.filter.SalesOrderFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.bizease.api.app.model.purchase_orders.specification.PurcharseOrdersSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

import java.util.List;

@Service
public class GetAllPurchaseOrdersUseCase {

    @Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    @Autowired
    private PurchaseOrdemItemRepository purchaseOrdemItemRepository;

    public PageReturn<List<PurchaseOrders>> execute(PurchaseOrderFilter filter) {

        Specification<PurchaseOrders> specification = where(
                commerceUuidEquals(filter.getCommerceUuid()).and(idEquals(filter.getId()))
                        .and(statusEquals(filter.getStatus())));

        Direction direction = Direction.valueOf(filter.getDirection().toUpperCase());

        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize(), direction, filter.getField());

        Page<PurchaseOrders> model = this.purchaseOrdersRepository.findAll(specification, pageRequest);

        List<PurchaseOrders> responses = model.getContent();

        responses.stream().forEach((response) -> {
            List<PurchaseOrderItem> purchaseOrderItems = this.purchaseOrdemItemRepository
                    .findByPurchaseOrdersId(response.getId());

            purchaseOrderItems.stream().forEach((salesItems) -> {
                salesItems.setPurchaseOrders(null);
            });

            response.setPurchaseOrderItems(purchaseOrderItems);
            ;
        });

        return new PageReturn<List<PurchaseOrders>>(responses, model.getTotalElements(), pageRequest.getPageNumber(),
                pageRequest.getPageSize());
    }
}
