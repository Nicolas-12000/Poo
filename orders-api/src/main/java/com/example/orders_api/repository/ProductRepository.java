package com.example.orders_api.repository;

import com.example.orders_api.model.Product;
import java.util.List;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByNameContainingIgnoreCase(String name);
	List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
	List<Product> findByNameContainingIgnoreCaseAndPriceBetween(String name, BigDecimal min, BigDecimal max);
}
