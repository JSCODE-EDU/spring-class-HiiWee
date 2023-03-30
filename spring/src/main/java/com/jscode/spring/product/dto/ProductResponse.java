package com.jscode.spring.product.dto;

import com.jscode.spring.product.domain.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(exclude = "id")
public class ProductResponse {

    @Schema(description = "상품 id", example = "1")
    private Long id;

    @Schema(description = "상품 이름", example = "응답 상품 이름")
    private String name;

    @Schema(description = "상품 가격", example = "3000")
    private double price;

    @Schema(description = "상품이 포함된 상점 id", example = "1")
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
