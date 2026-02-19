package com.example.orders_api.repository;

import com.example.orders_api.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByNameContainingIgnoreCase(String name);
	List<Product> findByPriceBetween(Double min, Double max);
	List<Product> findByNameContainingIgnoreCaseAndPriceBetween(String name, Double min, Double max);
}
