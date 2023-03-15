package com.jscode.spring.product.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Product {

    private Long id;
    private String name;
    private int price;

    public Product(final Long id, final String name, final int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void generateId(final Long id) {
        this.id = id;
    }

    public boolean contains(final Long id) {
        return Objects.equals(this.id, id);
    }

}
