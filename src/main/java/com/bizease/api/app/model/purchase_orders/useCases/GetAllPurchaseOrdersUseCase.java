package com.bizease.api.app.model.purchase_orders.useCases;

import com.bizease.api.app.model.purchase_orders.entities.PurchaseOrders;
import com.bizease.api.app.model.purchase_orders.repository.PurchaseOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllPurchaseOrdersUseCase {

    @Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    public List<PurchaseOrders> execute() {
        return this.purchaseOrdersRepository.findAll();
    }
}
