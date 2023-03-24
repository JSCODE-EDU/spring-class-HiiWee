package com.jscode.spring.product.repository;

import com.jscode.spring.product.domain.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private static final List<Product> store = new ArrayList<>();
    private static final AtomicLong counter = new AtomicLong();

    @PostConstruct
    void initialize() {
        save(new Product("컴퓨터", 3_000_000L));
        save(new Product("키보드", 100_000L));
        save(new Product("마우스", 50_000L));
    }

    public List<Product> findAll() {
        return Collections.unmodifiableList(store);
    }

    public List<Product> findAllByName(final String name) {
        return store.stream()
                .filter(product -> product.isSameName(name))
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<Product> findById(final Long id) {
        return store.stream()
                .filter(product -> product.isSameId(id))
                .findAny();
    }

    public Long save(final Product product) {
        product.generateId(counter.incrementAndGet());
        store.add(product);
        return product.getId();
    }

    public Optional<Product> findByName(final String name) {
        return store.stream()
                .filter(product -> product.isSameName(name))
                .findAny();
    }

}