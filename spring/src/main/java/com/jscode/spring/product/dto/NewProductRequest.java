package com.jscode.spring.product.dto;

import com.jscode.spring.product.domain.Product;
import lombok.Getter;

@Getter
public class NewProductRequest {

    private String name;
    private Long price;

    private NewProductRequest() {
    }

    public NewProductRequest(final String name, final Long price) {
        this.name = name;
        this.price = price;
    }

    public Product toDomain() {
        return new Product(name, price);
    }

}
