package com.jscode.spring.store.service;

import com.jscode.spring.store.domain.Store;
import com.jscode.spring.store.dto.StoreResponse;
import com.jscode.spring.store.dto.StoreSaveRequest;
import com.jscode.spring.store.dto.StoresResponse;
import com.jscode.spring.store.exception.StoreNotFoundException;
import com.jscode.spring.store.repository.StoreRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(final StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional
    public Long saveStore(final StoreSaveRequest saveRequest) {
        Store store = Store.builder()
                .name(saveRequest.getName())
                .address(saveRequest.getAddress())
                .phone(saveRequest.getPhone())
                .build();
        return storeRepository.save(store)
                .getId();
    }

    public StoreResponse findById(final Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(StoreNotFoundException::new);
        return StoreResponse.from(store);
    }

    public StoresResponse findAll() {
        List<Store> stores = storeRepository.findAll();
        List<StoreResponse> storesResponse = stores.stream()
                .map(StoreResponse::from)
                .collect(Collectors.toList());
        return new StoresResponse(storesResponse);
    }

}
