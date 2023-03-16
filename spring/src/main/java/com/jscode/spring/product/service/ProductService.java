package com.jscode.spring.product.service;

import com.jscode.spring.exchange.service.ExchangeRatesService;
import com.jscode.spring.product.domain.MonetaryUnit;
import com.jscode.spring.product.domain.Product;
import com.jscode.spring.product.dto.NewProductRequest;
import com.jscode.spring.product.dto.ProductListResponse;
import com.jscode.spring.product.dto.ProductResponse;
import com.jscode.spring.product.exception.DuplicateNameException;
import com.jscode.spring.product.exception.ProductNotFoundException;
import com.jscode.spring.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ExchangeRatesService exchangeRatesService;

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository, final ExchangeRatesService exchangeRatesService) {
        this.productRepository = productRepository;
        this.exchangeRatesService = exchangeRatesService;
    }

    public Long saveProduct(final NewProductRequest newProductRequest) {
        Product product = newProductRequest.toDomain();
        validateProductName(product);
        return productRepository.save(product);
    }

    private void validateProductName(final Product product) {
        Product findProduct = productRepository.findByName(product.getName());
        if (findProduct != null) {
            throw new DuplicateNameException();
        }
    }

    /**
     * TODO: 테스트하기 어려운 코드
     */
    public ProductListResponse findAll(@Nullable final String name, @Nullable final String monetaryUnit) {
        if (name == null) {
            List<Product> products = productRepository.findAll();
            return new ProductListResponse(createConvertedPriceProducts(monetaryUnit, products));
        }
        List<Product> products = productRepository.findAllByName(name);
        if (products.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return new ProductListResponse(createConvertedPriceProducts(monetaryUnit, products));
    }

    private List<ProductResponse> createConvertedPriceProducts(final String monetaryUnit, final List<Product> products) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            double convertedPrice = convertPriceKrwTo(monetaryUnit, product);
            ProductResponse productResponse = ProductResponse.of(product, convertedPrice);
            productResponses.add(productResponse);
        }
        return productResponses;
    }

    public ProductResponse findProductById(final Long productId, final String monetaryUnit) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new ProductNotFoundException();
        }
        double convertedPrice = convertPriceKrwTo(monetaryUnit, product);
        return ProductResponse.of(product, convertedPrice);
    }

    private double convertPriceKrwTo(final String monetaryUnit, final Product product) {
        if (monetaryUnit == null) {
            return product.getPrice();
        }
        return exchangeRatesService.convertKrwTo(MonetaryUnit.valueOf(monetaryUnit), product.getPrice());
    }

}
