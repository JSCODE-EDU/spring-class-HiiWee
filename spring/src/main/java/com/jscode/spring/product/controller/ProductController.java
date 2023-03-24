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

    @PostMapping("/products")
    public ProductSaveResponse saveProduct(@RequestBody final NewProductRequest newProductRequest) {
        log.info("call saveProduct");
        Long generatedId = productService.saveProduct(newProductRequest);
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

    // TODO 묶어보기: 파라미터가 여러개가 추가되는 상황에서 컨트롤러를 분리하는것이 좋을지 한번에 처리하는것이 좋을지?
    /**
     * (미션2) 상품 조회 메소드 구현(이름)
     */
    @GetMapping(value = "/products", params = {"name", "price"})
    public ProductListResponse findAllByPriceAndName(@RequestParam final Long price,
                                                     @RequestParam final String name) {
        log.info("call findAllByPriceAndName");
        return productService.findAllByPriceAndName(price, name);
    }

}
