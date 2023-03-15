package com.jscode.spring.product.dto;

import com.jscode.spring.product.domain.Product;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ProductListResponse {

    private List<ProductResponse> productResponses;

    private ProductListResponse(final List<ProductResponse> productResponses) {
        this.productResponses = productResponses;
    }

    public static ProductListResponse createListFrom(final List<Product> products) {
        return new ProductListResponse(products.stream()
                .map(ProductResponse::createResponseFrom)
                .collect(Collectors.toList())
        );
    }
}
