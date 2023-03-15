package com.jscode.spring.product.service;

import com.jscode.spring.exchange.service.ExchangeRatesService;
import com.jscode.spring.product.domain.Product;
import com.jscode.spring.product.dto.NewProductRequest;
import com.jscode.spring.product.dto.ProductResponse;
import com.jscode.spring.product.repository.ProductRepository;
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
        Product product = newProductRequest.toEntity();
        return productRepository.save(product);
    }

    public ProductResponse findProductUsdPrice(final Long productId) {
        Product product = productRepository.findById(productId);
        double usdPrice = exchangeRatesService.convertKrwToUsd(product.getPrice());
        return ProductResponse.createUsdPriceResponse(product, usdPrice);
    }
}
