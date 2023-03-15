package com.jscode.spring.product.dto;

import com.jscode.spring.product.domain.Product;
import lombok.Getter;

@Getter
public class ProductResponse {

    private Long id;
    private String name;
    private double price;

    private ProductResponse(final Long id, final String name, final double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse createUsdPriceResponse(final Product product, final double usdPrice) {
        return new ProductResponse(product.getId(), product.getName(), usdPrice);
    }

}
