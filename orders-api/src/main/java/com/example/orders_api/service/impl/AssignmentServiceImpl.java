package com.example.orders_api.service.impl;

import com.example.orders_api.model.Courier;
import com.example.orders_api.model.Order;
import com.example.orders_api.model.OrderStatus;
import com.example.orders_api.repository.CourierRepository;
import com.example.orders_api.repository.OrderRepository;
import com.example.orders_api.service.AssignmentService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;

    public AssignmentServiceImpl(CourierRepository courierRepository, OrderRepository orderRepository) {
        this.courierRepository = courierRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order assignCourier(Order order) {
        List<Courier> couriers = courierRepository.findAll();
        if (couriers.isEmpty()) return order; // nothing to assign
        Courier chosen = couriers.get(0);
        order.setCourier(chosen);
        order.setStatus(OrderStatus.ASSIGNED);
        return orderRepository.save(order);
    }
}
