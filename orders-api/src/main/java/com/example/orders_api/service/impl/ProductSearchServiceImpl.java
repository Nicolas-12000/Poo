package com.example.orders_api.service.impl;

import com.example.orders_api.model.Product;
import com.example.orders_api.repository.ProductRepository;
import com.example.orders_api.service.ProductSearchService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    private final ProductRepository productRepository;

    public ProductSearchServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> search(String name, Double minPrice, Double maxPrice) {
        boolean hasName = name != null && !name.isBlank();
        boolean hasMin = minPrice != null;
        boolean hasMax = maxPrice != null;

        if (hasName && hasMin && hasMax) {
            return productRepository.findByNameContainingIgnoreCaseAndPriceBetween(name, minPrice, maxPrice);
        }
        if (hasName && (hasMin || hasMax)) {
            Double min = hasMin ? minPrice : Double.valueOf(0.0);
            Double max = hasMax ? maxPrice : Double.valueOf(Double.MAX_VALUE);
            return productRepository.findByNameContainingIgnoreCaseAndPriceBetween(name, min, max);
        }
        if (hasName) {
            return productRepository.findByNameContainingIgnoreCase(name);
        }
        if (hasMin || hasMax) {
            Double min = hasMin ? minPrice : Double.valueOf(0.0);
            Double max = hasMax ? maxPrice : Double.valueOf(Double.MAX_VALUE);
            return productRepository.findByPriceBetween(min, max);
        }
        return productRepository.findAll();
    }
}
