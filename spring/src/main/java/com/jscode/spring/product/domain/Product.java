package com.jscode.spring.product.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long price;

    protected Product() {
    }

    public Product(final String name, final Long price) {
        this.name = name;
        this.price = price;
    }

    public void generateId(final Long id) {
        this.id = id;
    }

    public boolean isSameId(final Long id) {
        return Objects.equals(this.id, id);
    }

    public boolean isSameName(final String name) {
        return this.name.equals(name);
    }

}
