package com.example.orders_api.controller;

import com.example.orders_api.model.Courier;
import com.example.orders_api.repository.CourierRepository;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {

    private final CourierRepository courierRepository;

    public CourierController(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @GetMapping
    public List<Courier> list() {
        return courierRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Courier> get(@PathVariable Long id) {
        Optional<Courier> c = courierRepository.findById(id);
        return c.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Courier> create(@RequestBody Courier courier) {
        Courier saved = courierRepository.save(courier);
        return ResponseEntity.created(URI.create("/api/couriers/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Courier> update(@PathVariable Long id, @RequestBody Courier courier) {
        return courierRepository.findById(id).map(existing -> {
            existing.setName(courier.getName());
            existing.setAddress(courier.getAddress());
            existing.setVehicle(courier.getVehicle());
            Courier updated = courierRepository.save(existing);
            return ResponseEntity.ok(updated);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!courierRepository.existsById(id)) return ResponseEntity.notFound().build();
        courierRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
