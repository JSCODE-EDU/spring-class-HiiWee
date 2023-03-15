package com.jscode.spring.product.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.jscode.spring.product.domain.Product;
import com.jscode.spring.product.dto.NewProductRequest;
import com.jscode.spring.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("상품 저장 성공 테스트")
    void saveProduct() {
        productService.saveProduct(new NewProductRequest("test", 3000));
        Product product = productRepository.findById(4L);

        Assertions.assertAll(
                () -> assertThat(product.getName()).isEqualTo("test"),
                () -> assertThat(product.getPrice()).isEqualTo(3000)
        );

    }

}