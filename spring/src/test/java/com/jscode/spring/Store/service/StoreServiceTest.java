package com.jscode.spring.Store.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.jscode.spring.Store.domain.Address;
import com.jscode.spring.Store.domain.Store;
import com.jscode.spring.Store.dto.StoreSaveRequest;
import com.jscode.spring.Store.repository.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class StoreServiceTest {

    @Autowired
    StoreService storeService;

    @Autowired
    StoreRepository storeRepository;

    Store store;

    @BeforeEach
    void setUp() {
        store = Store.builder()
                .name("테스트상점")
                .address(new Address("도로명 테스트 주소", "상세 테스트 주소"))
                .phone("010-1234-1234")
                .build();

        storeRepository.save(store);
    }

    @DisplayName("상점을 저장한다.")
    @Test
    void saveProduct_success() {
        StoreSaveRequest saveRequest = StoreSaveRequest.builder()
                .name("상점")
                .address(new Address("도로명", "상세주소"))
                .phone("010-0000-0000")
                .build();

        Long savedId = storeService.saveStore(saveRequest);
        Store savedStore = storeRepository.findById(savedId).get();

        Assertions.assertAll(
                () -> assertThat(savedStore).isNotNull(),
                () -> assertThat(savedStore.getId()).isEqualTo(savedId),
                () -> assertThat(savedStore.getName()).isEqualTo("상점"),
                () -> assertThat(savedStore.getAddress()).isEqualTo(new Address("도로명", "상세주소")),
                () -> assertThat(savedStore.getPhone()).isEqualTo("010-0000-0000")
        );
    }

}