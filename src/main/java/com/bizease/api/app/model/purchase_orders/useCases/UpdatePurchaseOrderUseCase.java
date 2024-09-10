package com.bizease.api.app.model.purchase_orders.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.purchase_orders.dto.PurchaseOrdersRequestDTO;
import com.bizease.api.app.model.purchase_orders.entities.PurchaseOrders;
import com.bizease.api.app.model.purchase_orders.enums.StatusEnum;
import com.bizease.api.app.model.purchase_orders.repository.PurchaseOrdersRepository;
import com.bizease.api.app.model.suppliers.entities.Suppliers;
import com.bizease.api.app.model.suppliers.repository.SuppliersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdatePurchaseOrderUseCase {

    @Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    @Autowired
    private SuppliersRepository suppliersRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    public PurchaseOrders execute(UUID uuid, PurchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        PurchaseOrders updatedPurchaseOrder = this.purchaseOrdersRepository.findByUuid(uuid).orElseThrow(() -> {
           throw new NotFoundException("Pedido de compra");
        });

        Suppliers suppliers = this.suppliersRepository.findById(purchaseOrdersRequestDTO.getSup_id()).orElseThrow(() -> {
            throw new NotFoundException("Fornecedor");
        });

        Commerce commerce = this.commerceRepository.findById(purchaseOrdersRequestDTO.getCom_id()).orElseThrow(() -> {
            throw new NotFoundException("Comércio");
        });

        updatedPurchaseOrder.setStatus(StatusEnum.fromString(purchaseOrdersRequestDTO.getStatus()));
        updatedPurchaseOrder.setOrderDate(purchaseOrdersRequestDTO.getOrder_date());
        updatedPurchaseOrder.setExpectedDeliveryDate(purchaseOrdersRequestDTO.getExpected_delivery_date());
        updatedPurchaseOrder.setSuppliers(suppliers);
        updatedPurchaseOrder.setCommerce(commerce);

        return this.purchaseOrdersRepository.save(updatedPurchaseOrder);
    }
}
