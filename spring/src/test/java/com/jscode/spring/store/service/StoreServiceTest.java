package com.jscode.spring.store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jscode.spring.store.domain.Address;
import com.jscode.spring.store.domain.Store;
import com.jscode.spring.store.dto.StoreResponse;
import com.jscode.spring.store.dto.StoreSaveRequest;
import com.jscode.spring.store.dto.StoresResponse;
import com.jscode.spring.store.exception.StoreNotFoundException;
import com.jscode.spring.store.repository.StoreRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(readOnly = true)
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
    @Transactional
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

    @DisplayName("id를 통해 상점을 찾을 수 있다.")
    @Test
    void findById() {
        StoreResponse storeResponse = storeService.findById(store.getId());

        Assertions.assertAll(
                () -> assertThat(storeResponse.getId()).isEqualTo(store.getId()),
                () -> assertThat(storeResponse.getName()).isEqualTo(store.getName()),
                () -> assertThat(storeResponse.getAddress()).isEqualTo(store.getAddress()),
                () -> assertThat(storeResponse.getPhone()).isEqualTo(store.getPhone())
        );
    }

    @DisplayName("존재하지 않는 상점은 찾을 수 없다.")
    @Test
    void findById_exception_notExistId() {
        assertThatThrownBy(() -> storeService.findById(111111L))
                .isInstanceOf(StoreNotFoundException.class)
                .hasMessageContaining("상점을 찾을 수 없습니다.");
    }

    @DisplayName("모든 상점을 조회할 수 있다.")
    @Transactional
    @Test
    void findAll() {
        Store test1 = Store.builder().name("test1").build();
        Store test2 = Store.builder().name("test2").build();
        storeRepository.save(test1);
        storeRepository.save(test2);

        StoresResponse storesResponse = storeService.findAll();
        List<StoreResponse> stores = storesResponse.getStores();
        List<Long> storeIds = stores.stream()
                .map(StoreResponse::getId)
                .collect(Collectors.toList());

        assertThat(storeIds).containsOnly(store.getId(), test1.getId(), test2.getId());
    }

}