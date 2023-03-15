package com.jscode.spring.product.controller;

import com.jscode.spring.product.dto.NewProductRequest;
import com.jscode.spring.product.dto.ProductListResponse;
import com.jscode.spring.product.dto.ProductResponse;
import com.jscode.spring.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * (연습문제) 상품 등록 api
     */
    @PostMapping("/products")
    public String saveProduct(@RequestBody final NewProductRequest newProductRequest) {
        Long generatedId = productService.saveProduct(newProductRequest);
        return String.valueOf(generatedId);
    }

    /**
     * (연습문제) 상품 조회 api
     */
    @GetMapping("/products/{productId}")
    public ProductResponse findProduct(@PathVariable final Long productId) {
        return productService.findProductUsdPrice(productId);
    }

    /**
     * (연습문제) 전체 상품 조회
     */
    @GetMapping("/products")
    public ProductListResponse findProducts() {
        return productService.findAllProduct();
    }
}
