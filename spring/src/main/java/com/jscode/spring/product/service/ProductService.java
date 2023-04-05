package com.jscode.spring.product.service;

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
import com.jscode.spring.store.domain.Store;
import com.jscode.spring.store.exception.StoreNotFoundException;
import com.jscode.spring.store.repository.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ExchangeRatesService exchangeRatesService;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    public ProductService(final ProductRepository productRepository, final ExchangeRatesService exchangeRatesService,
                          final StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.exchangeRatesService = exchangeRatesService;
        this.storeRepository = storeRepository;
    }

    @Transactional
    public Long saveProduct(final ProductSaveRequest productRequest) {
        Store store = storeRepository.findById(productRequest.getStoreId())
                .orElseThrow(StoreNotFoundException::new);
        Product product = Product.createProduct(productRequest.getName(), productRequest.getPrice(), store);
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new DuplicateNameException();
        }
        return productRepository.save(product)
                .getId();
    }

    public ProductsResponse findAllByName(final String name, @Nullable final String monetaryUnit) {
        List<Product> products = productRepository.findAllByName(name);
        if (products.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return new ProductsResponse(createConvertedPriceProducts(monetaryUnit, products));
    }

    public ProductsResponse findAll(@Nullable final String monetaryUnit) {
        List<Product> products = productRepository.findAll();
        return new ProductsResponse(createConvertedPriceProducts(monetaryUnit, products));
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

    public ProductsResponse findAllByPriceOrderByName(final Long price) {
        List<Product> products = productRepository.findAllByPriceOrderByNameDesc(price);
        return ProductsResponse.from(products);
    }

    public ProductsResponse findAllByPriceAndName(final ProductContentRequest productContentRequest) {
        List<Product> products = productRepository.findAllByPriceAndName(
                productContentRequest.getPrice(),
                productContentRequest.getName()
        );
        return ProductsResponse.from(products);
    }

    public ProductsResponse findAllByStoreId(final Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(StoreNotFoundException::new);
        List<Product> products = productRepository.findAllByStore(store);
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return new ProductsResponse(productResponses);
    }
}
