package com.jscode.spring.product.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jscode.spring.product.dto.ProductResponse;
import com.jscode.spring.product.dto.ProductSaveRequest;
import com.jscode.spring.product.dto.ProductsResponse;
import com.jscode.spring.product.exception.DuplicateNameException;
import com.jscode.spring.product.exception.ProductNotFoundException;
import com.jscode.spring.product.service.ProductService;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    ProductSaveRequest productRequest1;
    ProductResponse productResponse1;
    ProductResponse productResponse2;

    @BeforeEach
    void setUp() {
        productRequest1 = ProductSaveRequest.builder()
                .name("newProduct")
                .price(3000L)
                .storeId(1L)
                .build();
        productResponse1 = ProductResponse.builder()
                .id(1L)
                .name("product1")
                .price(3000L)
                .storeId(1L)
                .build();
        productResponse2 = ProductResponse.builder()
                .id(2L)
                .name("product2")
                .price(3000L)
                .storeId(1L)
                .build();
    }

    @DisplayName("상품 등록 요청을 받으면 새로운 상품을 등록한다.")
    @Test
    void saveProduct() throws Exception {
        doReturn(1L).when(productService)
                .saveProduct(ArgumentMatchers.any());

        mockMvc.perform(post("/api/products")
                        .content(objectMapper.writeValueAsString(productRequest1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.savedId").value(1L))
                .andDo(print());
    }

    @DisplayName("동일한 상품 이름을 등록하면 400를 반환한다.")
    @Test
    void saveProduct_exception_duplicatedName() throws Exception {
        doThrow(new DuplicateNameException()).when(productService)
                .saveProduct(any());

        mockMvc.perform(post("/api/products")
                        .content(objectMapper.writeValueAsString(productRequest1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("동일한 이름의 상품은 저장할 수 없습니다."))
                .andDo(print());
    }

    @DisplayName("pathVariable id값을 통한 상품 조회 요청이 오면 상품을 반환한다.")
    @Test
    void findProductById() throws Exception {
        doReturn(productResponse1).when(productService)
                .findProductById(any(), any());

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(1L),
                        jsonPath("$.name").value("product1"),
                        jsonPath("$.price").value(3000))
                .andDo(print());
    }

    @DisplayName("존재하지 않는 id값을 통한 상품 조회 요청이 오면 404를 반환한다.")
    @Test
    void findProductById_exception_notExistId() throws Exception {
        doThrow(new ProductNotFoundException()).when(productService)
                .findProductById(any(), any());

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("존재하지 않는 상품입니다."))
                .andDo(print());
    }

    @DisplayName("모든 상품 조회 요청을 받으면 상품 리스트를 반환한다.")
    @Test
    void findProducts() throws Exception {
        List<ProductResponse> productResponses = List.of(productResponse1, productResponse2);
        doReturn(new ProductsResponse(productResponses)).when(productService)
                .findAll(any());

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.productResponses.size()").value(2),
                        jsonPath("$.productResponses[0].id").value(1L),
                        jsonPath("$.productResponses[1].id").value(2L))
                .andDo(print());
    }

    @DisplayName("특정 이름을 통해 상품 조회 요청을 받으면 상품 리스트를 반환한다.")
    @Test
    void findProductByQueryStringName() throws Exception {
        List<ProductResponse> productResponses = List.of(productResponse1);
        doReturn(new ProductsResponse(productResponses)).when(productService)
                .findAllByName(any(), any());

        mockMvc.perform(get("/api/products").param("name", "product1"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.productResponses.size()").value(1),
                        jsonPath("$.productResponses[0].id").value(1L),
                        jsonPath("$.productResponses[0].name").value("product1"),
                        jsonPath("$.productResponses[0].price").value(3000L))
                .andDo(print());
    }

    @DisplayName("없는 이름을 통해 상품 조회 요청을 받으면 404를 반환한다.")
    @Test
    void findProductByQueryStringName_exception_notExistName() throws Exception {
        doThrow(new ProductNotFoundException()).when(productService)
                .findAllByName(any(), any());

        mockMvc.perform(get("/api/products")
                        .param("name", "product1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("존재하지 않는 상품입니다."))
                .andDo(print());
    }

    @DisplayName("queryString id값을 통한 상품 조회 요청이 오면 상품을 반환한다.")
    @Test
    void findProductByQueryStringId() throws Exception {
        doReturn(productResponse1).when(productService)
                .findProductById(any(), any());

        mockMvc.perform(get("/api/products")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(1L),
                        jsonPath("$.name").value("product1"),
                        jsonPath("$.price").value(3000))
                .andDo(print());
    }

    @DisplayName("존재하지 않는 queryString id값을 통한 상품 조회 요청이 오면 404를 반환한다.")
    @Test
    void findProductByQueryStringId_exception_notExistId() throws Exception {
        doThrow(new ProductNotFoundException()).when(productService)
                .findProductById(any(), any());

        mockMvc.perform(get("/api/products")
                        .param("id", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("존재하지 않는 상품입니다."))
                .andDo(print());
    }

    @DisplayName("특정 가격을 기준으로 상품 조회 요청이 오면 이름 내림차순으로 정렬한 상품 리스트가 반환한다.")
    @Test
    void findAllProductByPriceOrderByName() throws Exception {
        List<ProductResponse> productResponses = List.of(productResponse2, productResponse1);
        doReturn(new ProductsResponse(productResponses)).when(productService)
                .findAllByPriceOrderByName(any());

        mockMvc.perform(get("/api/products")
                        .param("price", "3000"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.productResponses.size()").value(2),
                        jsonPath("$.productResponses[0].name").value("product2"),
                        jsonPath("$.productResponses[1].name").value("product1"))
                .andDo(print());
    }

    @DisplayName("특정 가격과 이름으로 상품 조회 요청이 오면 해당되는 상품 리스트를 반환한다.")
    @Test
    void findAllByPriceAndName() throws Exception {
        List<ProductResponse> productResponses = List.of(productResponse1);
        doReturn(new ProductsResponse(productResponses)).when(productService)
                .findAllByPriceAndName(any());

        mockMvc.perform(get("/api/products")
                        .param("name", "product1")
                        .param("price", "3000"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.productResponses.size()").value(1),
                        jsonPath("$.productResponses[0].id").value(1L),
                        jsonPath("$.productResponses[0].name").value("product1"))
                .andDo(print());
    }

    @DisplayName("특정 상점 번호로 상품 조회 요청이 오면 해당되는 상품 리스트를 반환한다.")
    @Test
    void findAllByStoreId_success() throws Exception {
        List<ProductResponse> products = List.of(productResponse1, productResponse2);
        doReturn(new ProductsResponse(products)).when(productService)
                .findAllByStoreId(any());

        mockMvc.perform(get("/api/products").param("storeId", "1"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.productResponses.size()").value(2),
                        jsonPath("$.productResponses[0].id").value(1L),
                        jsonPath("$.productResponses[1].id").value(2L)
                );
    }
}