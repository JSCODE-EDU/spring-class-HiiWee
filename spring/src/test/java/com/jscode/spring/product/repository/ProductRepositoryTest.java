package com.jscode.spring.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.jscode.spring.product.domain.Product;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductRepositoryTest {

    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    @DisplayName("전체 상품 조회 테스트")
    void findAll() {
        List<Product> products = productRepository.findAll();
        assertThat(products.size()).isNotZero();
    }

    @Test
    @DisplayName("특정 상품 조회 성공 테스트")
    void findById_success() {
        Product product = productRepository.findById(1L);
        assertThat(product.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("특정 상품 조회 실패시 예외 발생 테스트")
    void findById_fail_withException() {
        Assertions.assertThatThrownBy(() -> productRepository.findById(11111L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 상품입니다.");
    }
}