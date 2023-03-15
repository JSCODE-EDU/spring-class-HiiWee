package com.jscode.spring.product.controller;

import com.jscode.spring.product.dto.NewProductRequest;
import com.jscode.spring.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
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
     * (연습문제) 상품 등록 api 역할 구분하기
     */
    @PostMapping("/products")
    public String saveProduct(@RequestBody final NewProductRequest newProductRequest) {
        log.info("call");
        Long generatedId = productService.saveProduct(newProductRequest);
        return String.valueOf(generatedId);
    }

}
