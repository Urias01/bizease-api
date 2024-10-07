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

import java.time.LocalDate;

@Service
public class CreatePurchaseOrderUseCase {

    @Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    @Autowired
    private SuppliersRepository suppliersRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    public PurchaseOrders execute(PurchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        var suppliers = findSupplier(purchaseOrdersRequestDTO.getSupplierId());
        var commerce = findCommerce(purchaseOrdersRequestDTO.getCommerceId());

        validateDates(purchaseOrdersRequestDTO.getOrderDate(), purchaseOrdersRequestDTO.getExpectedDeliveryDate());

        StatusEnum status = StatusEnum.fromString(purchaseOrdersRequestDTO.getStatus());

        PurchaseOrders purchaseOrder = new PurchaseOrders();
        purchaseOrder.setStatus(status);
        purchaseOrder.setOrderDate(purchaseOrdersRequestDTO.getOrderDate());
        purchaseOrder.setExpectedDeliveryDate(purchaseOrdersRequestDTO.getExpectedDeliveryDate());
        purchaseOrder.setDeliveryDate(purchaseOrdersRequestDTO.getDeliveryDate());
        purchaseOrder.setSuppliers(suppliers);
        purchaseOrder.setCommerce(commerce);

        return purchaseOrdersRepository.save(purchaseOrder);
    }

    private void validateDates(LocalDate orderDate, LocalDate expectedDeliveryDate) {
        if (expectedDeliveryDate.isBefore(orderDate)) {
            throw new IllegalArgumentException("A data esperada de entrega não pode ser inferior à data do pedido.");
        }
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
