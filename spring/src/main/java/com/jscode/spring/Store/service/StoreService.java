package com.jscode.spring.Store.service;

import com.jscode.spring.Store.domain.Store;
import com.jscode.spring.Store.dto.StoreSaveRequest;
import com.jscode.spring.Store.repository.StoreRepository;
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

}
