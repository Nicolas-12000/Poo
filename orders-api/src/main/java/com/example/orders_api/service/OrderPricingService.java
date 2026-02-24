package com.example.orders_api.service;

import com.example.orders_api.model.Order;

public interface OrderPricingService {
    /**
     * Calculate and populate subtotal, total and related fields on the order.
     */
    void applyPricing(Order order);
}
