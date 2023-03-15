package com.jscode.spring.product.service;

import com.jscode.spring.product.domain.Product;
import com.jscode.spring.product.dto.NewProductRequest;
import com.jscode.spring.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Long saveProduct(final NewProductRequest newProductRequest) {
        Product product = newProductRequest.toEntity();
        return productRepository.save(product);
    }

}
