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

@Service
public class CreatePurchaseOrderUseCase {

    @Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    @Autowired
    private SuppliersRepository suppliersRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    public PurchaseOrders execute(PurchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        var suppliers = findSupplier(purchaseOrdersRequestDTO.getSup_id());
        var commerce = findCommerce(purchaseOrdersRequestDTO.getCom_id());

        StatusEnum status = StatusEnum.fromString(purchaseOrdersRequestDTO.getStatus());

        PurchaseOrders purchaseOrder = new PurchaseOrders();
        purchaseOrder.setStatus(status);
        purchaseOrder.setOrderDate(purchaseOrdersRequestDTO.getOrder_date());
        purchaseOrder.setExpectedDeliveryDate(purchaseOrdersRequestDTO.getExpected_delivery_date());
        purchaseOrder.setSuppliers(suppliers);
        purchaseOrder.setCommerce(commerce);

        return purchaseOrdersRepository.save(purchaseOrder);
    }

    private Suppliers findSupplier(Long id) {
        Suppliers suppliers = this.suppliersRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Fornecedor");
        });
        return suppliers;
    }

    private Commerce findCommerce(Long id) {
        Commerce commerce = this.commerceRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Comércio");
        });
        return commerce;
    }
}
