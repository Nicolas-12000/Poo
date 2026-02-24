package com.example.orders_api.repository;

import com.example.orders_api.model.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	List<Customer> findByNameContainingIgnoreCase(String name);
	List<Customer> findByAddressCityContainingIgnoreCase(String city);
	List<Customer> findByPhoneContaining(String phone);
	List<Customer> findByNameContainingIgnoreCaseAndAddressCityContainingIgnoreCase(String name, String city);
}
