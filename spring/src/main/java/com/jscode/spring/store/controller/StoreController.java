package com.jscode.spring.store.controller;

import com.jscode.spring.config.ApiDocumentResponse;
import com.jscode.spring.store.dto.StoreResponse;
import com.jscode.spring.store.dto.StoreSaveRequest;
import com.jscode.spring.store.dto.StoreSaveResponse;
import com.jscode.spring.store.dto.StoresResponse;
import com.jscode.spring.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Store Controller", description = "상점 컨트롤러")
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
    @ApiDocumentResponse
    @Operation(summary = "saveStore", description = "상점 저장")
    @PostMapping("/stores")
    public StoreSaveResponse saveStore(@Valid @RequestBody final StoreSaveRequest saveRequest) {
        log.info("call saveStore");
        Long savedId = storeService.saveStore(saveRequest);
        return new StoreSaveResponse(savedId);
    }

    /**
     * 전체 상점 조회 api
     */
    @ApiDocumentResponse
    @Operation(summary = "findAll", description = "모든 상점 조회")
    @GetMapping("/stores")
    public StoresResponse findAll() {
        log.info("call findAll");
        return storeService.findAll();
    }

    /**
     * 상세 상점 조회 api
     */
    @ApiDocumentResponse
    @Operation(summary = "findById", description = "상점 조회")
    @GetMapping("/stores/{storeId}")
    public StoreResponse findById(@PathVariable final Long storeId) {
        log.info("call findById");
        return storeService.findById(storeId);
    }
}

