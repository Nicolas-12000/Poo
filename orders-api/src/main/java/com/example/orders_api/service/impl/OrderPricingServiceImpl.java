package com.example.orders_api.service.impl;

import com.example.orders_api.model.Order;
import com.example.orders_api.model.OrderItem;
import com.example.orders_api.service.OrderPricingService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class OrderPricingServiceImpl implements OrderPricingService {

    @Override
    public void applyPricing(Order order) {
        if (order == null) return;
        BigDecimal sum = BigDecimal.ZERO;
        if (order.getItems() != null) {
            for (OrderItem it : order.getItems()) {
                if (it != null) sum = sum.add(it.subtotal() == null ? BigDecimal.ZERO : it.subtotal());
            }
        }
        order.setSubtotal(sum);

        BigDecimal discountPercent = order.getDiscountPercent() == null ? BigDecimal.ZERO : order.getDiscountPercent();
        BigDecimal taxPercent = order.getTaxPercent() == null ? BigDecimal.ZERO : order.getTaxPercent();

        BigDecimal discountAmount = sum.multiply(discountPercent).divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP);
        BigDecimal taxed = (sum.subtract(discountAmount)).multiply(taxPercent).divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP);
        BigDecimal total = sum.subtract(discountAmount).add(taxed).setScale(2, RoundingMode.HALF_UP);
        order.setTotal(total);
    }
}
