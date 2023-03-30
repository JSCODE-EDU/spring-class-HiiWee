package com.jscode.spring.product.dto;

import com.jscode.spring.product.domain.Product;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(exclude = "id")
public class ProductResponse {

    private Long id;
    private String name;
    private double price;
    private Long storeId;

    @Builder
    private ProductResponse(final Long id, final String name, final double price, final Long storeId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.storeId = storeId;
    }

    public static ProductResponse of(final Product product, final double price) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(price)
                .storeId(product.getStoreId())
                .build();
    }

    public static ProductResponse from(final Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .storeId(product.getStoreId())
                .build();
    }

}
