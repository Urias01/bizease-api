package com.bizease.api.app.model.purchase_orders.useCases;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.purchase_order_items.entities.PurchaseOrderItem;
import com.bizease.api.app.model.purchase_order_items.repository.PurchaseOrdemItemRepository;
import com.bizease.api.app.model.purchase_orders.entities.PurchaseOrders;
import com.bizease.api.app.model.purchase_orders.repository.PurchaseOrdersRepository;

@Service
public class GetPurchaseOrderByUuid {

  @Autowired
  private PurchaseOrdersRepository purchaseOrdersRepository;

  @Autowired
  private PurchaseOrdemItemRepository purchaseOrdemItemRepository;

  public PurchaseOrders execute(String uuid) {

    Optional<PurchaseOrders> purchaseOrderExists = this.purchaseOrdersRepository.findByUuid(UUID.fromString(uuid));

    if (!purchaseOrderExists.isPresent()) {
      throw new NotFoundException("Pedido de venda");
    }

    PurchaseOrders model = purchaseOrderExists.get();

    List<PurchaseOrderItem> purchaseOrderItems = this.purchaseOrdemItemRepository.findByPurchaseOrdersId(model.getId());

    purchaseOrderItems.stream().forEach((purchaseItem) -> {
      purchaseItem.setPurchaseOrders(null);
    });

    model.setPurchaseOrderItems(purchaseOrderItems);

    return model;
  }
}
