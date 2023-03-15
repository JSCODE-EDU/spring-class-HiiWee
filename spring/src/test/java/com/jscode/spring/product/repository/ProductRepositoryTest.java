package com.jscode.spring.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.jscode.spring.product.domain.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductRepositoryTest {

    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void findAll() {
        List<Product> products = productRepository.findAll();
        assertThat(products.size()).isEqualTo(3);
    }
}