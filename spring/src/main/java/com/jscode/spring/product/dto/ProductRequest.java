package com.jscode.spring.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductRequest {

    @Schema(description = "이름", example = "상품 이름", required = true)
    private String name;

    @Schema(description = "가격", example = "10000", required = true)
    private Long price;

    @Schema(description = "상점 id", example = "1", required = true)
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
