package com.bizease.api.app.model.purchase_orders.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.purchase_orders.entities.PurchaseOrders;
import com.bizease.api.app.model.purchase_orders.enums.StatusEnum;
import com.bizease.api.app.model.purchase_orders.repository.PurchaseOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdatePurchaseOrderStatusUseCase {

    @Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    public PurchaseOrders execute(UUID uuid, StatusEnum newStatus) {
        PurchaseOrders purchaseOrders = this.purchaseOrdersRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new NotFoundException("Pedido de compra");
        });

        StatusEnum currentStatus = purchaseOrders.getStatus();
        StatusEnum updatedStatus = currentStatus.transitionTo(newStatus);

        purchaseOrders.setStatus(updatedStatus);
        return this.purchaseOrdersRepository.save(purchaseOrders);
    }
}
