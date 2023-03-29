package com.jscode.spring.Store.domain;

import com.jscode.spring.product.domain.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Address address;

    private String phone;

    @OneToMany(mappedBy = "store")
    private List<Product> products = new ArrayList<>();

    protected Store() {
    }

    @Builder
    private Store(final Long id, final String name, final Address address, final String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public void addProduct(final Product product) {
        products.add(product);
    }
}
