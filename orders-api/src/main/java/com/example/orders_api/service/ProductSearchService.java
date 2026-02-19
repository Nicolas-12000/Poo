package com.example.orders_api.service;

import com.example.orders_api.model.Product;
import java.util.List;

public interface ProductSearchService {
    List<Product> search(String name, Double minPrice, Double maxPrice);
}
