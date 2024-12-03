package com.bizease.api.app.model.purchase_order_items.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.repository.ProductsRepository;
import com.bizease.api.app.model.purchase_order_items.dto.PurchaseOrderItemDTO;
import com.bizease.api.app.model.purchase_order_items.entities.PurchaseOrderItem;
import com.bizease.api.app.model.purchase_order_items.repository.PurchaseOrdemItemRepository;
import com.bizease.api.app.model.purchase_orders.entities.PurchaseOrders;
import com.bizease.api.app.model.purchase_orders.repository.PurchaseOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatePurchaseOrderItemUseCase {

    @Autowired
    private PurchaseOrdemItemRepository purchaseOrdemItemRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    public PurchaseOrderItem execute(PurchaseOrderItemDTO purchaseOrdemItemDTO) {
        var product = findProduct(purchaseOrdemItemDTO.getProductId());
        var purchaseOrder = findPurchaseOrder(purchaseOrdemItemDTO.getPurchaseOrderId());

        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
        purchaseOrderItem.setQuantity(purchaseOrdemItemDTO.getQuantity());
        purchaseOrderItem.setUnitPrice(purchaseOrdemItemDTO.getUnitPrice());
        purchaseOrderItem.setExpirationDate(purchaseOrdemItemDTO.getExpirationDate());
        purchaseOrderItem.setProducts(product);
        purchaseOrderItem.setPurchaseOrders(purchaseOrder);

        return purchaseOrdemItemRepository.save(purchaseOrderItem);
    }

    private Products findProduct(Long id) {
        return this.productsRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Produto");
        });
    }

    private PurchaseOrders findPurchaseOrder(Long id) {
        return this.purchaseOrdersRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Pedido de compra");
        });
    }
}
