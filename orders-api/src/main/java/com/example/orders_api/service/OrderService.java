package com.example.orders_api.service;

import com.example.orders_api.model.Order;
import com.example.orders_api.model.OrderStatus;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order create(Order order);
    Order findById(UUID id);
    List<Order> findAll();
    Order changeStatus(UUID id, OrderStatus status);
}
