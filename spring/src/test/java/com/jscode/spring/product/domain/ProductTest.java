package com.jscode.spring.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jscode.spring.Store.domain.Address;
import com.jscode.spring.Store.domain.Store;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    Store store;
    Product product;

    @BeforeEach
    void setUp() {
        store = Store.builder()
                .name("상점1")
                .phone("010-1234-1234")
                .address(new Address("xx로", "202"))
                .build();

        product = Product.builder()
                .name("test")
                .price(3000L)
                .store(store)
                .build();

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

    @Test
    @DisplayName("상품이 등록되면 반드시 상점 정보가 있어야 한다.")
    void registerStore() {
        product.registerStore(store);

        Assertions.assertAll(
                () -> assertThat(product.getStore()).isEqualTo(store),
                () -> assertThat(store.getProducts()).contains(product)
        );
    }
}