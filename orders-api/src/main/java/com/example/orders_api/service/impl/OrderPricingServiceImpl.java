package com.example.orders_api.service.impl;

import com.example.orders_api.model.Order;
import com.example.orders_api.model.OrderItem;
import com.example.orders_api.service.OrderPricingService;
import org.springframework.stereotype.Service;

@Service
public class OrderPricingServiceImpl implements OrderPricingService {

    @Override
    public void applyPricing(Order order) {
        if (order == null) return;
        double sum = 0.0;
        if (order.getItems() != null) {
            for (OrderItem it : order.getItems()) {
                if (it != null) sum += it.subtotal();
            }
        }
        order.setSubtotal(sum);

        double discountPercent = order.getDiscountPercent() == null ? 0.0 : order.getDiscountPercent();
        double taxPercent = order.getTaxPercent() == null ? 0.0 : order.getTaxPercent();

        double discountAmount = sum * discountPercent / 100.0;
        double taxed = (sum - discountAmount) * taxPercent / 100.0;
        double total = sum - discountAmount + taxed;
        order.setTotal(total);
    }
}
