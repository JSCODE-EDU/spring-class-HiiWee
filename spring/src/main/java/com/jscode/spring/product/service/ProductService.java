package com.jscode.spring.product.service;

import com.jscode.spring.exchange.service.ExchangeRatesService;
import com.jscode.spring.product.domain.Product;
import com.jscode.spring.product.dto.NewProductRequest;
import com.jscode.spring.product.dto.ProductListResponse;
import com.jscode.spring.product.dto.ProductResponse;
import com.jscode.spring.product.exception.DuplicateNameException;
import com.jscode.spring.product.exception.ProductNotFoundException;
import com.jscode.spring.product.repository.ProductRepository;
import java.util.List;
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

    // TODO: IllegalArgument? IllegalState?
    public ProductResponse findProductUsdPrice(final Long productId) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new ProductNotFoundException();
        }
        double usdPrice = exchangeRatesService.convertKrwToUsd(product.getPrice());
        return ProductResponse.createUsdPriceResponseOf(product, usdPrice);
    }

    /**
     * TODO: 테스트하기 어려운 코드
     */
    public ProductListResponse findAllProduct() {
        List<Product> products = productRepository.findAll();
        return ProductListResponse.createListFrom(products);
    }
}
