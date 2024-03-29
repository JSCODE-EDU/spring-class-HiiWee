package com.jscode.spring.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jscode.spring.exchange.service.ExchangeRatesService;
import com.jscode.spring.product.domain.MonetaryUnit;
import com.jscode.spring.product.domain.Product;
import com.jscode.spring.product.dto.ProductContentRequest;
import com.jscode.spring.product.dto.ProductResponse;
import com.jscode.spring.product.dto.ProductSaveRequest;
import com.jscode.spring.product.dto.ProductsResponse;
import com.jscode.spring.product.exception.DuplicateNameException;
import com.jscode.spring.product.exception.ProductNotFoundException;
import com.jscode.spring.product.repository.ProductRepository;
import com.jscode.spring.store.domain.Address;
import com.jscode.spring.store.domain.Store;
import com.jscode.spring.store.repository.StoreRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(readOnly = true)
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ExchangeRatesService exchangeRatesService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StoreRepository storeRepository;

    Product product1;
    Product product2;
    Product product3;
    Store store;

    @BeforeEach
    void setUp() {
        store = Store.builder()
                .name("상점")
                .address(new Address("로", "번지"))
                .phone("010-0000-0000")
                .build();
        product1 = Product.createProduct("컴퓨터", 3_000_000L, store);
        product2 = Product.createProduct("키보드", 100_000L, store);
        product3 = Product.createProduct("마우스", 50_000L, store);

        storeRepository.save(store);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }

    @DisplayName("상품 저장 성공 테스트")
    @Transactional
    @Test
    void saveProduct_success() {
        Long id = productService.saveProduct(new ProductSaveRequest("test", 3000L, store.getId()));
        Product product = productRepository.findById(id).get();

        Assertions.assertAll(
                () -> assertThat(product.getId()).isEqualTo(id),
                () -> assertThat(product.getName()).isEqualTo("test"),
                () -> assertThat(product.getPrice()).isEqualTo(3000),
                () -> assertThat(product.getStore().getId()).isEqualTo(store.getId())
        );
    }

    @DisplayName("전체 상품 조회 성공 테스트")
    @Test
    void findAll_success() {
        ProductsResponse products = productService.findAll(null);

        Assertions.assertAll(
                () -> assertThat(products.contains(ProductResponse.of(product1, 3000000))).isTrue(),
                () -> assertThat(products.contains(ProductResponse.of(product2, 100000))).isTrue(),
                () -> assertThat(products.contains(ProductResponse.of(product3, 50000))).isTrue()
        );
    }

    @DisplayName("존재하는 name에 대한 전체 상품 조회 성공")
    @Test
    void findAllByName_success() {
        ProductsResponse productListResponse = productService.findAllByName("컴퓨터", null);

        assertThat(
                productListResponse.contains(
                        ProductResponse.of(new Product("컴퓨터", 3_000_000L, store), 3000000))).isTrue();
    }

    @DisplayName("없는 name에 대한 전체 상품 조회 실패 테스트")
    @Test
    void findAllByName_fail_withInvalidName() {
        String name = "nothing";

        assertThatThrownBy(() -> productService.findAllByName(name, null))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("존재하지 않는 상품입니다.");
    }

    @DisplayName("동일 이름 상품 저장시 예외 발생 테스트")
    @Transactional
    @Test
    void saveDuplicateNameProduct_fail_withException() {
        ProductSaveRequest request1 = new ProductSaveRequest("sameName", 3000L, store.getId());
        ProductSaveRequest request2 = new ProductSaveRequest("sameName", 5000L, store.getId());

        productService.saveProduct(request1);

        assertThatThrownBy(() -> productService.saveProduct(request2))
                .isInstanceOf(DuplicateNameException.class)
                .hasMessageContaining("동일한 이름의 상품은 저장할 수 없습니다.");
    }

    @DisplayName("단순 상품 ID 조회")
    @Test
    void findProductById_success() {
        ProductResponse productById = productService.findProductById(product1.getId(), null);

        assertThat(productById.getPrice()).isEqualTo((double) product1.getPrice());
    }

    @Disabled
    @DisplayName("상품 ID 및 KRW 단위로 조회 성공 테스트")
    @Test
    void findProductById_success_withKRW_monetaryUnit() {
        ProductResponse productByName = productService.findProductById(product1.getId(), "KRW");

        assertThat(productByName.getPrice()).isEqualTo((double) product1.getPrice());
    }

    @Disabled
    @DisplayName("상품 ID 및 USD 단위로 조회 성공 테스트")
    @Test
    void findProductById_success_withUSD_monetaryUnit() {
        double usdPrice = exchangeRatesService.convertKrwTo(MonetaryUnit.USD, product1.getPrice());

        ProductResponse productByName = productService.findProductById(product1.getId(), "USD");

        assertThat(productByName.getPrice()).isEqualTo(usdPrice);
    }

    @DisplayName("상품 가격으로 조회시 이름 내림차순으로 조회")
    @Transactional
    @Test
    void findAllByPriceOrderByName() {
        Product samePriceProduct1 = new Product("samePriceProduct1", 3000L, store);
        Product samePriceProduct2 = new Product("samePriceProduct2", 3000L, store);
        Product samePriceProduct3 = new Product("samePriceProduct3", 3000L, store);
        productRepository.save(samePriceProduct1);
        productRepository.save(samePriceProduct2);
        productRepository.save(samePriceProduct3);

        ProductsResponse findProducts = productService.findAllByPriceOrderByName(3000L);

        assertThat(findProducts.getProductResponses()).containsExactly(
                ProductResponse.of(samePriceProduct3, 3000L),
                ProductResponse.of(samePriceProduct2, 3000L),
                ProductResponse.of(samePriceProduct1, 3000L)
        );
    }

    @DisplayName("상품 가격, 이름으로 조회")
    @Test
    void findAllByPriceAndName() {
        ProductsResponse products = productService.findAllByPriceAndName(
                new ProductContentRequest("컴퓨터", 3000000L));
        assertThat(products.contains(ProductResponse.of(product1, product1.getPrice()))).isTrue();
    }

    @DisplayName("상점 번호에 해당되는 모든 상품 조회")
    @Test
    void findAllByStoreId() {
        Product product = new Product("newProduct", 100000L, Store.builder().build());
        productRepository.save(product);

        ProductsResponse productsResponse = productService.findAllByStoreId(store.getId());
        List<ProductResponse> productResponses = productsResponse.getProductResponses();
        List<Long> productIds = productResponses.stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toList());

        assertThat(productIds).containsOnly(product1.getId(), product2.getId(), product3.getId());
    }
}