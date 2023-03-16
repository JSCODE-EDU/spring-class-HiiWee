package com.jscode.spring.product.dto;

import lombok.Getter;

@Getter
public class ProductSaveResponse {

    private Long savedId;

    public ProductSaveResponse(final Long savedId) {
        this.savedId = savedId;
    }

}
