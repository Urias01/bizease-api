package com.bizease.api.app.model.purchase_order_items.useCases;

import com.bizease.api.app.model.purchase_order_items.entities.PurchaseOrderItem;
import com.bizease.api.app.model.purchase_order_items.repository.PurchaseOrdemItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllPurchaseOrderItemsUseCase {

    @Autowired
    private PurchaseOrdemItemRepository purchaseOrdemItemRepository;

    public List<PurchaseOrderItem> execute() {
        return this.purchaseOrdemItemRepository.findAll();
    }
}
