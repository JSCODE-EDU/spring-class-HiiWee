package com.jscode.spring.product.dto;

import com.jscode.spring.product.domain.Product;
import lombok.Getter;

@Getter
public class NewProductRequest {

    private String name;
    private int price;

    private NewProductRequest() {
    }

    public NewProductRequest(final String name, final int price) {
        this.name = name;
        this.price = price;
    }

    public Product toDomain() {
        return new Product(null, name, price);
    }

}
