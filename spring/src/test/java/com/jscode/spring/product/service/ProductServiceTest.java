package com.jscode.spring.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jscode.spring.exchange.service.ExchangeRatesService;
import com.jscode.spring.product.domain.MonetaryUnit;
import com.jscode.spring.product.domain.Product;
import com.jscode.spring.product.dto.NewProductRequest;
import com.jscode.spring.product.dto.ProductListResponse;
import com.jscode.spring.product.dto.ProductResponse;
import com.jscode.spring.product.exception.DuplicateNameException;
import com.jscode.spring.product.exception.ProductNotFoundException;
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
    ExchangeRatesService exchangeRatesService;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("상품 저장 성공 테스트")
    void saveProduct_success() {
        Long id = productService.saveProduct(new NewProductRequest("test", 3000));
        Product product = productRepository.findById(id).get();

        Assertions.assertAll(
                () -> assertThat(product.getName()).isEqualTo("test"),
                () -> assertThat(product.getPrice()).isEqualTo(3000)
        );
    }

    @Test
    @DisplayName("전체 상품 조회 성공 테스트")
    void findAll_success() {
        ProductResponse productResponse1 = ProductResponse.of(new Product(1L, "컴퓨터", 3_000_000), 3000000);
        ProductResponse productResponse2 = ProductResponse.of(new Product(2L, "키보드", 100_000), 100000);
        ProductResponse productResponse3 = ProductResponse.of(new Product(3L, "마우스", 50_000), 50000);

        ProductListResponse products = productService.findAll( null);

        Assertions.assertAll(
                () -> assertThat(products.contains(productResponse1)).isTrue(),
                () -> assertThat(products.contains(productResponse2)).isTrue(),
                () -> assertThat(products.contains(productResponse3)).isTrue()
        );
    }

    @Test
    @DisplayName("존재하는 name에 대한 전체 상품 조회 성공")
    void findAllByName_success() {
        String name = "컴퓨터";

        ProductListResponse productListResponse = productService.findAllByName(name, null);

        assertThat(
                productListResponse.contains(ProductResponse.of(new Product(1L, "컴퓨터", 3_000_000), 3000000))).isTrue();
    }

    @Test
    @DisplayName("없는 name에 대한 전체 상품 조회 실패 테스트")
    void findAllByName_fail_withInvalidName() {
        String name = "nothing";

        assertThatThrownBy(() -> productService.findAllByName(name, null))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("존재하지 않는 상품입니다.");
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

    @Test
    @DisplayName("단순 상품 ID 조회")
    void findProductById_success() {
        Long id = productService.saveProduct(new NewProductRequest("basicTest1", 3000));

        ProductResponse productById = productService.findProductById(id, null);

        assertThat(productById.getPrice()).isEqualTo(3000.0);
    }

    @Test
    @DisplayName("상품 ID 및 KRW 단위로 조회 성공 테스트")
    void findProductById_success_withKRW_monetaryUnit() {
        Long id = productService.saveProduct(new NewProductRequest("test1", 3000));

        ProductResponse productByName = productService.findProductById(id, "KRW");

        assertThat(productByName.getPrice()).isEqualTo(3000.0);
    }

    @Test
    @DisplayName("상품 ID 및 USD 단위로 조회 성공 테스트")
    void findProductById_success_withUSD_monetaryUnit() {
        Long id = productService.saveProduct(new NewProductRequest("test2", 10000));
        double usdPrice = exchangeRatesService.convertKrwTo(MonetaryUnit.USD, 10000);

        ProductResponse productByName = productService.findProductById(id, "USD");

        assertThat(productByName.getPrice()).isEqualTo(usdPrice);
    }

}