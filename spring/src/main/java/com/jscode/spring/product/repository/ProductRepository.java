package com.jscode.spring.product.repository;

import com.jscode.spring.product.domain.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByName(final String name);

    Optional<Product> findByName(final String name);

    List<Product> findAllByPriceOrderByNameDesc(final Long price);

    List<Product> findAllByPriceAndName(Long price, String name);

    // 전체 상품 조회시 name이 특정 이름인 상품 무시
    @Query("SELECT p FROM Product p WHERE p.name <> :excludeName")
    List<Product> findAllByNameExceptExcludeName(@Param("excludeName") final String excludeName);

    // 가장 가격이 비싼 상품 조회하기
    @Query("SELECT p FROM Product p WHERE p.price = (SELECT max(sp.price) FROM Product sp )")
    List<Product> findAllWithMaxPrice();

    // 이름에 "컴"을 포함하는 상품 조회하기
    @Query("SELECT p FROM Product p WHERE p.name like :nameFormat")
    List<Product> findAllByNameIsLikeNameFormat(@Param("nameFormat") final String nameFormat);

    // 가장 가격이 저렴한 상품의 이름만 조회
    @Query("SELECT p.name FROM Product p WHERE p.price = (SELECT min(sp.price) FROM Product sp)")
    List<String> findNamesWithMinPrice();

    // 상품 가격의 평균 구하기
    @Query("SELECT avg(p.price) FROM Product p")
    Long findAveragePrice();

}
