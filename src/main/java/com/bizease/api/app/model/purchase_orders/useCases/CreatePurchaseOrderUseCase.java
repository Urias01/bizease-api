package com.bizease.api.app.model.purchase_orders.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.repository.ProductsRepository;
import com.bizease.api.app.model.purchase_order_items.dto.PurchaseOrderItemDTO;
import com.bizease.api.app.model.purchase_order_items.entities.PurchaseOrderItem;
import com.bizease.api.app.model.purchase_order_items.repository.PurchaseOrdemItemRepository;
import com.bizease.api.app.model.purchase_orders.dto.PurchaseOrdersRequestDTO;
import com.bizease.api.app.model.purchase_orders.entities.PurchaseOrders;
import com.bizease.api.app.model.purchase_orders.enums.StatusEnum;
import com.bizease.api.app.model.purchase_orders.repository.PurchaseOrdersRepository;
import com.bizease.api.app.model.suppliers.entities.Suppliers;
import com.bizease.api.app.model.suppliers.repository.SuppliersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CreatePurchaseOrderUseCase {

    @Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    @Autowired
    private SuppliersRepository suppliersRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private PurchaseOrdemItemRepository purcharseOrdemItemRepositoy;

    public Long execute(PurchaseOrdersRequestDTO purchaseOrdersRequestDTO) {
        System.out.println(purchaseOrdersRequestDTO.getSupplierUuid());
        var suppliers = findSupplier(purchaseOrdersRequestDTO.getSupplierUuid());
        var commerce = findCommerce(purchaseOrdersRequestDTO.getCommerceUuid());

        validateDates(purchaseOrdersRequestDTO.getOrderDate(), purchaseOrdersRequestDTO.getExpectedDeliveryDate());

        StatusEnum status = StatusEnum.fromString(purchaseOrdersRequestDTO.getStatus());

        PurchaseOrders purchaseOrder = new PurchaseOrders();
        purchaseOrder.setStatus(status);
        purchaseOrder.setOrderDate(purchaseOrdersRequestDTO.getOrderDate());
        purchaseOrder.setExpectedDeliveryDate(purchaseOrdersRequestDTO.getExpectedDeliveryDate());
        purchaseOrder.setDeliveryDate(purchaseOrdersRequestDTO.getExpectedDeliveryDate());
        purchaseOrder.setSuppliers(suppliers);
        purchaseOrder.setCommerce(commerce);
        final PurchaseOrders purchaseOrderFinal = this.purchaseOrdersRepository.save(purchaseOrder);

        List<PurchaseOrderItemDTO> purcharseOrderItemDTO = purchaseOrdersRequestDTO.getPurcharseOrderItems();

        if (purcharseOrderItemDTO.size() > 0) {
            purcharseOrderItemDTO.parallelStream().forEach((purcharseOrderItem) -> {
                Optional<Products> productExists = this.productsRepository
                        .findByUuid(purcharseOrderItem.getProductUuid());

                if (!productExists.isPresent()) {
                    throw new NotFoundException("Produto");
                }

                Products product = productExists.get();

                product.setUnit(product.getUnit() + purcharseOrderItem.getQuantity());
                this.productsRepository.save(product);

                PurchaseOrderItem obj = new PurchaseOrderItem(purcharseOrderItem, product, purchaseOrderFinal);
                this.purcharseOrdemItemRepositoy.save(obj);
            });
        }

        return purchaseOrderFinal.getId();
    }

    private void validateDates(LocalDate orderDate, LocalDate expectedDeliveryDate) {
        if (expectedDeliveryDate.isBefore(orderDate)) {
            throw new IllegalArgumentException("A data esperada de entrega não pode ser inferior à data do pedido.");
        }
    }

    private Suppliers findSupplier(String uuid) {
        Suppliers suppliers = this.suppliersRepository.findByUuid(UUID.fromString(uuid)).orElseThrow(() -> {
            throw new NotFoundException("Fornecedor");
        });
        return suppliers;
    }

    private Commerce findCommerce(String uuid) {
        Commerce commerce = this.commerceRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new NotFoundException("Comércio");
        });
        return commerce;
    }
}
