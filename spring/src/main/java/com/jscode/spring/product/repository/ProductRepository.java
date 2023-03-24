package com.jscode.spring.product.repository;

import com.jscode.spring.product.domain.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByName(final String name);

    Optional<Product> findByName(final String name);

    List<Product> findAllByPriceOrderByNameDesc(final Long price);

    List<Product> findAllByPriceAndName(Long price, String name);

}
