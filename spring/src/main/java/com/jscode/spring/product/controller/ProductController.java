package com.jscode.spring.product.controller;

import com.jscode.spring.config.ApiDocumentResponse;
import com.jscode.spring.product.dto.ProductRequest;
import com.jscode.spring.product.dto.ProductResponse;
import com.jscode.spring.product.dto.ProductSaveResponse;
import com.jscode.spring.product.dto.ProductsResponse;
import com.jscode.spring.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Product Controller", description = "상품 관련 컨트롤러")
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @ApiDocumentResponse
    @Operation(summary = "saveProduct", description = "상품 등록")
    @PostMapping("/products")
    public ProductSaveResponse saveProduct(@RequestBody final ProductRequest productRequest) {
        log.info("call saveProduct");
        Long generatedId = productService.saveProduct(productRequest);
        return new ProductSaveResponse(generatedId);
    }

    @ApiDocumentResponse
    @Operation(summary = "findProductById", description = "상품 조회(가격 환율 변환 기능)")
    @GetMapping("/products/{productId}")
    public ProductResponse findProductById(@PathVariable final Long productId,
                                           @RequestParam @Nullable final String monetaryUnit) {
        log.info("call findProductById");
        return productService.findProductById(productId, monetaryUnit);
    }

    @ApiDocumentResponse
    @Operation(summary = "findProducts", description = "전체 상품 조회(가격 환율 변환 기능)")
    @GetMapping("/products")
    public ProductsResponse findProducts(@RequestParam @Nullable final String monetaryUnit) {
        log.info("call findProducts");
        return productService.findAll(monetaryUnit);
    }

    @ApiDocumentResponse
    @Operation(summary = "findProductByQueryStringName", description = "이름에 대한 상품 조회(가격 환율 변환 가능")
    @GetMapping(value = "/products", params = "name")
    public ProductsResponse findProductByQueryStringName(@RequestParam final String name,
                                                         @RequestParam @Nullable final String monetaryUnit) {
        log.info("call findProductByQueryStringName");
        return productService.findAllByName(name, monetaryUnit);
    }

    @ApiDocumentResponse
    @Operation(summary = "findProductByQueryStringId", description = "상품 조회(가격 환율 변환 기능)")
    @GetMapping(value = "/products", params = "id")
    public ProductResponse findProductByQueryStringId(@RequestParam final Long id,
                                                      @RequestParam @Nullable final String monetaryUnit) {
        log.info("call findProductByQueryStringId");
        return productService.findProductById(id, monetaryUnit);
    }

    @ApiDocumentResponse
    @Operation(summary = "findAllProductByPriceOrderByName", description = "특정 가격에 대해 이름 기준 내림차순 정렬한 모든 상품 조회")
    @GetMapping(value = "/products", params = "price")
    public ProductsResponse findAllProductByPriceOrderByName(@RequestParam final Long price) {
        log.info("call findAllProductByPriceOrderByName");
        return productService.findAllByPriceOrderByName(price);
    }

    @ApiDocumentResponse
    @Operation(summary = "findAllByPriceAndName", description = "특정 가격, 이름에 대한 모든 상품 조회")
    @GetMapping(value = "/products", params = {"name", "price"})
    public ProductsResponse findAllByPriceAndName(final ProductRequest productRequest) {
        log.info("call findAllByPriceAndName");
        return productService.findAllByPriceAndName(productRequest);
    }

    /**
     * 하나의 상점에 속하는 모든 상품 조회 api
     */
    @ApiDocumentResponse
    @Operation(summary = "findAllByStoreId", description = "특정 상점에 대한 모든 상품 조회")
    @GetMapping(value = "/products", params = "storeId")
    public ProductsResponse findAllByStoreId(@RequestParam final Long storeId) {
        return productService.findAllByStoreId(storeId);
    }

}
