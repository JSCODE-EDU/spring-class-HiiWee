package com.jscode.spring.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProductSaveResponse {

    @Schema(description = "저장된 상품 id", example = "1")
    private Long savedId;

    public ProductSaveResponse(final Long savedId) {
        this.savedId = savedId;
    }

}
