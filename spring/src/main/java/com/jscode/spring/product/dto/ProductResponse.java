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

    @Builder
    private ProductResponse(final Long id, final String name, final double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(final Product product, final double price) {
        return new ProductResponse(product.getId(), product.getName(), price);
    }

}
