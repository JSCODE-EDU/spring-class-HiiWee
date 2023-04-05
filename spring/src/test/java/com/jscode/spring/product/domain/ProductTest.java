package com.jscode.spring.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    Product product;

    @BeforeEach
    void setUp() {
        product = new Product("test", 3000L);
        product.generateId(1L);
    }

    @Test
    @DisplayName("동일 상품 id 확인")
    void contains() {
        assertThat(product.isSameId(1L)).isTrue();
    }

    @Test
    @DisplayName("동일 상품 이름 조회")
    void isSameName() {
        assertThat(product.isSameName("test")).isTrue();
    }

}