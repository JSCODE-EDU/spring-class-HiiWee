package com.jscode.spring.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.jscode.spring.product.domain.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductMemoryRepositoryTest {

    ProductMemoryRepository productMemoryRepository;

    @BeforeEach
    void setUp() {
        productMemoryRepository = new ProductMemoryRepository();
        productMemoryRepository.initialize();
    }

    @Test
    @DisplayName("전체 상품 조회 테스트")
    void findAll() {
        List<Product> products = productMemoryRepository.findAll();
        assertThat(products.size()).isNotZero();
    }

    @Test
    @DisplayName("특정 상품 조회 성공 테스트")
    void findById_success() {
        Product product = productMemoryRepository.findById(1L).get();
        assertThat(product.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("특정 상품 조회 실패시 null값 반환 테스트")
    void findById_fail_withNull() {
        assertThat(productMemoryRepository.findById(1111111111L)).isEmpty();
    }
}