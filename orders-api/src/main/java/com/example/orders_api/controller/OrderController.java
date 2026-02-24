package com.example.orders_api.controller;

import com.example.orders_api.model.Order;
import com.example.orders_api.model.OrderStatus;
import com.example.orders_api.service.AssignmentService;
import com.example.orders_api.service.NotificationService;
import com.example.orders_api.service.OrderService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final AssignmentService assignmentService;
    private final NotificationService notificationService;

    public OrderController(OrderService orderService, AssignmentService assignmentService, NotificationService notificationService) {
        this.orderService = orderService;
        this.assignmentService = assignmentService;
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        Order saved = orderService.create(order);
        notificationService.notify(saved.getCustomer(), "Order created: " + saved.getId());
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Order> list() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable UUID id) {
        Order o = orderService.findById(id);
        if (o == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(o);
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<Order> assign(@PathVariable UUID id) {
        Order o = orderService.findById(id);
        if (o == null) return ResponseEntity.notFound().build();
        Order updated = assignmentService.assignCourier(o);
        notificationService.notify(updated.getCustomer(), "Courier assigned: " + (updated.getCourier() == null ? "none" : updated.getCourier().getName()));
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> changeStatus(@PathVariable UUID id, @RequestBody OrderStatus status) {
        Order updated = orderService.changeStatus(id, status);
        notificationService.notify(updated.getCustomer(), "Order status: " + status);
        return ResponseEntity.ok(updated);
    }
}
