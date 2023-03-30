package com.jscode.spring.store.controller;

import com.jscode.spring.store.dto.StoreSaveRequest;
import com.jscode.spring.store.dto.StoreSaveResponse;
import com.jscode.spring.store.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class StoreController {

    private final StoreService storeService;

    public StoreController(final StoreService storeService) {
        this.storeService = storeService;
    }

    /**
     * 상점 등록 api
     */
    @PostMapping("/stores")
    public StoreSaveResponse saveStore(@RequestBody final StoreSaveRequest saveRequest) {
        Long savedId = storeService.saveStore(saveRequest);
        return new StoreSaveResponse(savedId);
    }

    /**
     * 전체 상점 조회 api
     */

    /**
     * 상세 상점 조회 api
     */
}

