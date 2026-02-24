package com.example.orders_api.service.impl;

import com.example.orders_api.model.Customer;
import com.example.orders_api.repository.CustomerRepository;
import com.example.orders_api.service.CustomerSearchService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerSearchServiceImpl implements CustomerSearchService {

    private final CustomerRepository customerRepository;

    public CustomerSearchServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> search(String name, String city, String phone) {
        boolean hasName = name != null && !name.isBlank();
        boolean hasCity = city != null && !city.isBlank();
        boolean hasPhone = phone != null && !phone.isBlank();

        if (hasName && hasCity) {
            return customerRepository.findByNameContainingIgnoreCaseAndAddressCityContainingIgnoreCase(name, city);
        }
        if (hasName) {
            return customerRepository.findByNameContainingIgnoreCase(name);
        }
        if (hasCity) {
            return customerRepository.findByAddressCityContainingIgnoreCase(city);
        }
        if (hasPhone) {
            return customerRepository.findByPhoneContaining(phone);
        }
        return customerRepository.findAll();
    }
}
