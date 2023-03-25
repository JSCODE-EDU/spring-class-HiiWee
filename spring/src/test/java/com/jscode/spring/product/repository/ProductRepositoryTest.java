package com.jscode.spring.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.jscode.spring.product.domain.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    Product product1;
    Product product2;
    Product product3;
    Product product4;

    @BeforeEach
    void setUp() {
        product1 = new Product("컴퓨터사인펜", 3000L);
        product2 = new Product("컴퓨터", 3000000L);
        product3 = new Product("모니터", 500000L);
        product4 = new Product("키보드", 100000L);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
    }

    @Test
    @DisplayName("특정 이름을 제외하는 모든 상품 조회")
    void findAllByNameExceptExcludeName() {
        List<Product> products = productRepository.findAllByNameExceptExcludeName("모니터");

        assertThat(products).containsOnly(product1, product2, product4);
    }

    @Test
    @DisplayName("가장 가격이 비싼 상품 조회하기")
    void findProductsWithMaxPrice() {
        List<Product> products = productRepository.findProductsWithMaxPrice();

        assertThat(products).containsOnly(product2);
    }

    @Test
    @DisplayName("특정 이름을 포함하는 모든 상품 조회")
    void findAllByNameIsLike() {
        List<Product> products = productRepository.findAllByNameIsLikeNameFormat("컴%");

        assertThat(products).containsOnly(product1, product2);
    }

    @Test
    @DisplayName("가장 가격이 저렴한 상품의 이름만 조회")
    void findNamesWithMinPrice() {
        List<String> productNames = productRepository.findNamesWithMinPrice();

        assertThat(productNames).containsOnly("컴퓨터사인펜");
    }

    @Test
    @DisplayName("상품 가격의 평균 구하기")
    void findAveragePrice() {
        Long expectPrice = (product1.getPrice() + product2.getPrice() + product3.getPrice() + product4.getPrice()) / 4;

        Long averagePrice = productRepository.findAveragePrice();

        assertThat(averagePrice).isEqualTo(expectPrice);
    }
}