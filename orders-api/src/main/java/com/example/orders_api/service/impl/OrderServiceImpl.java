package com.example.orders_api.service.impl;

import com.example.orders_api.model.Order;
import com.example.orders_api.model.OrderItem;
import com.example.orders_api.model.OrderStatus;
import com.example.orders_api.repository.OrderRepository;
import com.example.orders_api.service.OrderService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order create(Order order) {
        // ensure unitPrice is set from product price if missing
        for (OrderItem it : order.getItems()) {
            if (it.getUnitPrice() == 0 && it.getProduct() != null) {
                it.setUnitPrice(it.getProduct().getPrice());
            }
        }
        return orderRepository.save(order);
    }

    @Override
    public Order findById(UUID id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order changeStatus(UUID id, OrderStatus status) {
        var o = orderRepository.findById(id).orElseThrow();
        o.setStatus(status);
        return orderRepository.save(o);
    }
}
