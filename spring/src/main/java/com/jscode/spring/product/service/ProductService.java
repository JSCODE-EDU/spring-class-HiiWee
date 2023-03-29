package com.jscode.spring.product.service;

import com.jscode.spring.Store.domain.Store;
import com.jscode.spring.Store.service.StoreService;
import com.jscode.spring.exchange.service.ExchangeRatesService;
import com.jscode.spring.product.domain.MonetaryUnit;
import com.jscode.spring.product.domain.Product;
import com.jscode.spring.product.dto.ProductListResponse;
import com.jscode.spring.product.dto.ProductRequest;
import com.jscode.spring.product.dto.ProductResponse;
import com.jscode.spring.product.exception.DuplicateNameException;
import com.jscode.spring.product.exception.ProductNotFoundException;
import com.jscode.spring.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ExchangeRatesService exchangeRatesService;
    private final StoreService storeService;

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository, final ExchangeRatesService exchangeRatesService,
                          final StoreService storeService) {
        this.productRepository = productRepository;
        this.exchangeRatesService = exchangeRatesService;
        this.storeService = storeService;
    }

    @Transactional
    public Long saveProduct(final ProductRequest productRequest) {
        Store store = storeService.findById(productRequest.getStoreId());
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .store(store)
                .build();
        product.registerStore(store);
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new DuplicateNameException();
        }
        return productRepository.save(product)
                .getId();
    }

    public ProductListResponse findAllByName(final String name, @Nullable final String monetaryUnit) {
        List<Product> products = productRepository.findAllByName(name);
        if (products.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return new ProductListResponse(createConvertedPriceProducts(monetaryUnit, products));
    }

    public ProductListResponse findAll(@Nullable final String monetaryUnit) {
        List<Product> products = productRepository.findAll();
        return new ProductListResponse(createConvertedPriceProducts(monetaryUnit, products));
    }

    private List<ProductResponse> createConvertedPriceProducts(@Nullable final String monetaryUnit,
                                                               final List<Product> products) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            double convertedPrice = convertPriceKrwTo(monetaryUnit, product);
            ProductResponse productResponse = ProductResponse.of(product, convertedPrice);
            productResponses.add(productResponse);
        }
        return productResponses;
    }

    public ProductResponse findProductById(final Long productId, @Nullable final String monetaryUnit) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        double convertedPrice = convertPriceKrwTo(monetaryUnit, product);
        return ProductResponse.of(product, convertedPrice);
    }

    private double convertPriceKrwTo(final String monetaryUnit, final Product product) {
        if (Objects.isNull(monetaryUnit)) {
            return product.getPrice();
        }
        return exchangeRatesService.convertKrwTo(MonetaryUnit.valueOf(monetaryUnit), product.getPrice());
    }

    public ProductListResponse findAllByPriceOrderByName(final Long price) {
        List<Product> products = productRepository.findAllByPriceOrderByNameDesc(price);
        return ProductListResponse.from(products);
    }

    public ProductListResponse findAllByPriceAndName(final ProductRequest productRequest) {
        List<Product> products = productRepository.findAllByPriceAndName(
                productRequest.getPrice(),
                productRequest.getName()
        );
        return ProductListResponse.from(products);
    }

}
