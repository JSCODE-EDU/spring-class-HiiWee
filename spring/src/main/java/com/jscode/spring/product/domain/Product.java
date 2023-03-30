package com.jscode.spring.product.domain;

import com.jscode.spring.store.domain.Store;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    protected Product() {
    }

    @Builder
    public Product(final String name, final Long price, final Store store) {
        this.name = name;
        this.price = price;
        this.store = store;
    }

    public void registerStore(final Store store) {
        this.store = store;
        store.addProduct(this);
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

    public Long getStoreId() {
        return store.getId();
    }

}
