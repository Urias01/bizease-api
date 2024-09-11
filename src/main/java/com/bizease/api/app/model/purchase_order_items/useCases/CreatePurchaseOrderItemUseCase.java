package com.bizease.api.app.model.purchase_order_items.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.categories.entities.Categories;
import com.bizease.api.app.model.categories.repository.CategoriesRepository;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.purchase_order_items.dto.PurchaseOrderItemDTO;
import com.bizease.api.app.model.purchase_order_items.entities.PurchaseOrderItem;
import com.bizease.api.app.model.purchase_order_items.repository.PurchaseOrdemItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatePurchaseOrderItemUseCase {

    @Autowired
    private PurchaseOrdemItemRepository purchaseOrdemItemRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    public PurchaseOrderItem execute(PurchaseOrderItemDTO purchaseOrdemItemDTO) {
        var commerce = findCommerce(purchaseOrdemItemDTO.getCom_id());
        var categories = findCategories(purchaseOrdemItemDTO.getCat_id());

        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
        purchaseOrderItem.setQuantity(purchaseOrdemItemDTO.getQuantity());
        purchaseOrderItem.setUnitPrice(purchaseOrdemItemDTO.getUnit_price());
        purchaseOrderItem.setUnitSellingPrice(purchaseOrdemItemDTO.getUnit_selling_price());
        purchaseOrderItem.setCommerce(commerce);
        purchaseOrderItem.setCategories(categories);

        return purchaseOrdemItemRepository.save(purchaseOrderItem);
    }

    private Commerce findCommerce(Long id) {
        return this.commerceRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Comércio");
        });
    }

    private Categories findCategories(Long id) {
        return this.categoriesRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Categoria");
        });
    }
}
