package com.bizease.api.app.model.sales_orders.useCases;

import com.bizease.api.app.model.sales_orders.repository.SalesOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class GetRevenueByPeriodUseCase {

    @Autowired
    private SalesOrdersRepository salesOrdersRepository;

    public List<Map<String, Object>> execute(Long comId, LocalDate startDate, LocalDate endDate) {
        return this.salesOrdersRepository.findRevenueByPeriod(comId, startDate, endDate);
    }
}
