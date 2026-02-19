package com.example.orders_api.controller;

import com.example.orders_api.model.Customer;
import com.example.orders_api.repository.CustomerRepository;
import com.example.orders_api.service.CustomerSearchService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerSearchService customerSearchService;
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerSearchService customerSearchService,
                              CustomerRepository customerRepository) {
        this.customerSearchService = customerSearchService;
        this.customerRepository = customerRepository;
    }

    @GetMapping

    public List<Customer> list() {
        return customerSearchService.search(null, null, null);
    }

    @GetMapping("/search")
    public List<Customer> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String phone) {
        return customerSearchService.search(name, city, phone);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(@PathVariable Long id) {
        Optional<Customer> c = customerRepository.findById(id);
        return c.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        Customer saved = customerRepository.save(customer);
        return ResponseEntity.created(URI.create("/api/customers/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
        return customerRepository.findById(id).map(existing -> {
            existing.setName(customer.getName());
            existing.setAddress(customer.getAddress());
            existing.setPhone(customer.getPhone());
            Customer updated = customerRepository.save(existing);
            return ResponseEntity.ok(updated);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!customerRepository.existsById(id)) return ResponseEntity.notFound().build();
        customerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
