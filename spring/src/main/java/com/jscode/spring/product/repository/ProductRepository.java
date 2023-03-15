package com.jscode.spring.product.repository;

import com.jscode.spring.product.domain.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private static final List<Product> store = new ArrayList<>();
    private static Long id = 3L;

    static {
        store.add(new Product(1L, "컴퓨터", 3_000_000));
        store.add(new Product(2L, "키보드", 100_000));
        store.add(new Product(3L, "마우스", 50_000));
    }

    public List<Product> findAll() {
        return Collections.unmodifiableList(store);
    }

    public Product findById(final Long id) {
        return store.stream()
                .filter(product -> product.contains(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    public Long save(final Product product) {
        product.generateId(++id);
        store.add(product);
        return product.getId();
    }

}
