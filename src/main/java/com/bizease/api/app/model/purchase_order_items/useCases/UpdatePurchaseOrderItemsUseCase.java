package com.bizease.api.app.model.purchase_order_items.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.repository.ProductsRepository;
import com.bizease.api.app.model.purchase_order_items.dto.PurchaseOrderItemDTO;
import com.bizease.api.app.model.purchase_order_items.entities.PurchaseOrderItem;
import com.bizease.api.app.model.purchase_order_items.repository.PurchaseOrdemItemRepository;
import com.bizease.api.app.model.purchase_orders.entities.PurchaseOrders;
import com.bizease.api.app.model.purchase_orders.repository.PurchaseOrdersRepository;

@Service
public class UpdatePurchaseOrderItemsUseCase {

    @Autowired
    private PurchaseOrdemItemRepository purchaseOrdemItemRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    public Long execute(String uuid, PurchaseOrderItemDTO purchaseOrderItemDTO) {

        Optional<Products> productExists = this.productsRepository.findById(purchaseOrderItemDTO.getProductId());
        if (!productExists.isPresent()) {
            throw new NotFoundException("Produto");
        }

        Optional<PurchaseOrders> purchaseOrderExists = this.purchaseOrdersRepository
                .findById(purchaseOrderItemDTO.getPurchaseOrderId());
        if (!purchaseOrderExists.isPresent()) {
            throw new NotFoundException("Pedido de compra");
        }

        Optional<PurchaseOrderItem> purchaseOrderItemExists = this.purchaseOrdemItemRepository
                .findByUuid(UUID.fromString(uuid));
        if (!purchaseOrderItemExists.isPresent()) {
            throw new NotFoundException("Item do pedido de compra");
        }

        Products product = productExists.get();
        PurchaseOrders purchaseOrders = purchaseOrderExists.get();
        PurchaseOrderItem purchaseOrderItem = purchaseOrderItemExists.get();

        purchaseOrderItem.setQuantity(purchaseOrderItemDTO.getQuantity());
        purchaseOrderItem.setUnitPrice(purchaseOrderItemDTO.getUnitPrice());
        purchaseOrderItem.setExpirationDate(purchaseOrderItemDTO.getExpirationDate());
        purchaseOrderItem.setProducts(product);
        purchaseOrderItem.setPurchaseOrders(purchaseOrders);

        purchaseOrderItem = this.purchaseOrdemItemRepository.save(purchaseOrderItem);
        return purchaseOrderItem.getId();
    }

}
