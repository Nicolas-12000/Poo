package com.example.orders_api.service;

import com.example.orders_api.model.Customer;
import java.util.List;

public interface CustomerSearchService {
    List<Customer> search(String name, String city, String phone);
}
