package com.jscode.spring.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductRequest {

    private String name;
    private Long price;
    private Long storeId;

    private ProductRequest() {
    }

    @Builder
    public ProductRequest(final String name, final Long price, final Long storeId) {
        this.name = name;
        this.price = price;
        this.storeId = storeId;
    }


}
