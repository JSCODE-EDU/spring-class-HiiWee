package com.jscode.spring.product.controller;

import com.jscode.spring.product.dto.NewProductRequest;
import com.jscode.spring.product.dto.ProductListResponse;
import com.jscode.spring.product.dto.ProductResponse;
import com.jscode.spring.product.dto.ProductSaveResponse;
import com.jscode.spring.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    /**
     * (연습문제 1 ) 상품 등록 api <br>
     * (미션 1 ) 상품 등록 api (동일상품 등록시 실패)
     */
    @PostMapping("/products")
    public ProductSaveResponse saveProduct(@RequestBody final NewProductRequest newProductRequest) {
        Long generatedId = productService.saveProduct(newProductRequest);
        return new ProductSaveResponse(generatedId);
    }

    /**
     * (연습문제 2 ) 상품 조회 api
     */
    @GetMapping("/products/{productId}")
    public ProductResponse findProductById(@PathVariable final Long productId,
                                           @RequestParam @Nullable final String monetaryUnit) {
        return productService.findProductById(productId, monetaryUnit);
    }

    /**
     * (연습문제 3 ) 전체 상품 조회 (미션) 상품 이름으로 상세조회하는 api <br>
     * (미션 2 ) 상품 상세 조회 api
     */
    @GetMapping("/products")
    public ProductListResponse findProducts(@RequestParam @Nullable final String name,
                                            @RequestParam @Nullable final String monetaryUnit) {
        return productService.findAll(name, monetaryUnit);
    }

}
