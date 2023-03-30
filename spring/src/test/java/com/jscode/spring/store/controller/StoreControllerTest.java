package com.jscode.spring.store.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jscode.spring.store.domain.Address;
import com.jscode.spring.store.dto.StoreResponse;
import com.jscode.spring.store.dto.StoreSaveRequest;
import com.jscode.spring.store.dto.StoresResponse;
import com.jscode.spring.store.exception.StoreNotFoundException;
import com.jscode.spring.store.service.StoreService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StoreService storeService;

    StoreSaveRequest storeSaveRequest;
    StoreResponse storeResponse1;
    StoreResponse storeResponse2;
    Address address;

    @BeforeEach
    void setUp() {
        address = new Address("도로명", "상세주소");
        storeSaveRequest = StoreSaveRequest.builder()
                .name("상점1")
                .address(address)
                .phone("010-0000-0000")
                .build();
        storeResponse1 = StoreResponse.builder()
                .id(1L)
                .name("저장 상점1")
                .address(address)
                .phone("010-1111-1111")
                .build();
        storeResponse2 = StoreResponse.builder()
                .id(2L)
                .name("저장 상점2")
                .address(address)
                .phone("010-2222-2222")
                .build();
    }

    @DisplayName("상점 등록 요청을 받으면 새로운 상점이 등록된다.")
    @Test
    void saveStore() throws Exception {
        doReturn(1L).when(storeService)
                .saveStore(any());

        mockMvc.perform(post("/api/stores")
                        .content(objectMapper.writeValueAsString(storeSaveRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.savedId").value(1L))
                .andDo(print());
    }

    @DisplayName("상점 전체 조회 요청이 오면 모든 상점 정보를 반환한다.")
    @Test
    void findAll() throws Exception {
        List<StoreResponse> stores = List.of(storeResponse1, storeResponse2);

        doReturn(new StoresResponse(stores)).when(storeService)
                .findAll();

        mockMvc.perform(get("/api/stores"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.stores.size()").value(2),
                        jsonPath("$.stores[0].id").value(1L),
                        jsonPath("$.stores[1].id").value(2L))
                .andDo(print());
    }

    @DisplayName("특정 상점 조회 요청이 오면 해당 상점 정보를 반환한다.")
    @Test
    void findById_success() throws Exception {
        doReturn(storeResponse1).when(storeService)
                .findById(any());

        mockMvc.perform(get("/api/stores/1"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(1L),
                        jsonPath("$.name").value("저장 상점1"),
                        jsonPath("$.address.street").value("도로명"),
                        jsonPath("$.address.detail").value("상세주소"),
                        jsonPath("$.phone").value("010-1111-1111"))
                .andDo(print());
    }

    @DisplayName("특정 상점 조회 요청이 오면 해당 상점 정보를 반환한다.")
    @Test
    void findById_exception_invalidStoreId() throws Exception {
        doThrow(new StoreNotFoundException()).when(storeService)
                .findById(any());

        mockMvc.perform(get("/api/stores/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("상점을 찾을 수 없습니다."))
                .andDo(print());
    }

}