package com.bizease.api.app.model.purchase_order_items.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.purchase_order_items.entities.PurchaseOrderItem;
import com.bizease.api.app.model.purchase_order_items.repository.PurchaseOrdemItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetPurchaseOrderItemUseCase {

    @Autowired
    PurchaseOrdemItemRepository purchaseOrdemItemRepository;

    public PurchaseOrderItem execute(UUID uuid) {
        return this.purchaseOrdemItemRepository.findByUuid(uuid).orElseThrow(() -> {
                   throw new NotFoundException("Item do pedido de compra");
        });
    }
}
