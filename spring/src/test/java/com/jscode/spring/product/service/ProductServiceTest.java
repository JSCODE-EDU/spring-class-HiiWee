package com.jscode.spring.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jscode.spring.product.domain.Product;
import com.jscode.spring.product.dto.NewProductRequest;
import com.jscode.spring.product.exception.DuplicateNameException;
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
    void saveProduct_success() {
        productService.saveProduct(new NewProductRequest("test", 3000));
        Product product = productRepository.findById(4L);

        Assertions.assertAll(
                () -> assertThat(product.getName()).isEqualTo("test"),
                () -> assertThat(product.getPrice()).isEqualTo(3000)
        );
    }

    @Test
    @DisplayName("동일 이름 상품 저장시 예외 발생 테스트")
    void saveDuplicateNameProduct_fail_withException() {
        NewProductRequest request1 = new NewProductRequest("sameName", 3000);
        NewProductRequest request2 = new NewProductRequest("sameName", 5000);

        productService.saveProduct(request1);

        assertThatThrownBy(() -> productService.saveProduct(request2))
                .isInstanceOf(DuplicateNameException.class)
                .hasMessageContaining("동일한 이름의 상품은 저장할 수 없습니다.");
    }
}