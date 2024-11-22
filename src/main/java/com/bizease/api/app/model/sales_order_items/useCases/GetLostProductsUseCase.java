package com.bizease.api.app.model.sales_order_items.useCases;

import com.bizease.api.app.model.sales_order_items.repository.SalesOrderItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GetLostProductsUseCase {

    @Autowired
    private SalesOrderItemsRepository salesOrderItemsRepository;

    public List<Map<String, Object>> execute(Long comId) {
        return this.salesOrderItemsRepository.findLostProducts(comId);
    }
}
