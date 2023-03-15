package com.jscode.spring.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    @DisplayName("동일 상품 id 확인")
    void contains() {
        Product product = new Product(1L, "test", 3000);

        assertThat(product.contains(1L)).isTrue();
    }
}