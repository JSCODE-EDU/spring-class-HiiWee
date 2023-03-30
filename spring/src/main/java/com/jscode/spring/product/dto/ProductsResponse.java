package com.jscode.spring.product.dto;

import com.jscode.spring.product.domain.Product;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ProductsResponse {

    private List<ProductResponse> productResponses;

    public ProductsResponse(final List<ProductResponse> productResponses) {
        this.productResponses = productResponses;
    }

    public static ProductsResponse from(final List<Product> products) {
        return new ProductsResponse(
                products.stream()
                        .map(product -> ProductResponse.of(product, product.getPrice()))
                        .collect(Collectors.toList())
        );
    }

    public boolean contains(final ProductResponse productResponse) {
        return productResponses.contains(productResponse);
    }

}