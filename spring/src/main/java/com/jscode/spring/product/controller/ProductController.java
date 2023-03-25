package com.jscode.spring.product.controller;

import com.jscode.spring.product.dto.ProductListResponse;
import com.jscode.spring.product.dto.ProductRequest;
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

    @PostMapping("/products")
    public ProductSaveResponse saveProduct(@RequestBody final ProductRequest productRequest) {
        log.info("call saveProduct");
        Long generatedId = productService.saveProduct(productRequest);
        return new ProductSaveResponse(generatedId);
    }

    @GetMapping("/products/{productId}")
    public ProductResponse findProductById(@PathVariable final Long productId,
                                           @RequestParam @Nullable final String monetaryUnit) {
        log.info("call findProductById");
        return productService.findProductById(productId, monetaryUnit);
    }

    @GetMapping("/products")
    public ProductListResponse findProducts(@RequestParam @Nullable final String monetaryUnit) {
        log.info("call findProducts");
        return productService.findAll(monetaryUnit);
    }

    @GetMapping(value = "/products", params = "name")
    public ProductListResponse findProductByQueryStringName(@RequestParam final String name,
                                                            @RequestParam @Nullable final String monetaryUnit) {
        log.info("call findProductByQueryStringName");
        return productService.findAllByName(name, monetaryUnit);
    }

    /**
     * (미션1) 상품 상세 조회 구현
     */
    @GetMapping(value = "/products", params = "id")
    public ProductResponse findProductByQueryStringId(@RequestParam final Long id,
                                                      @RequestParam @Nullable final String monetaryUnit) {
        log.info("call findProductByQueryStringId");
        return productService.findProductById(id, monetaryUnit);
    }

    /**
     * (미션2) 상품 조회 메소드 구현(가격)
     */
    @GetMapping(value = "/products", params = "price")
    public ProductListResponse findAllProductByPriceOrderByName(@RequestParam final Long price) {
        log.info("call findAllProductByPriceOrderByName");
        return productService.findAllByPriceOrderByName(price);
    }

    /**
     * (미션2) 상품 조회 메소드 구현(이름)
     */
    @GetMapping(value = "/products", params = {"name", "price"})
    public ProductListResponse findAllByPriceAndName(final ProductRequest productRequest) {
        log.info("call findAllByPriceAndName");
        return productService.findAllByPriceAndName(productRequest);
    }

}
