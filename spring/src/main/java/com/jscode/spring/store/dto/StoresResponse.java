package com.jscode.spring.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
public class StoresResponse {

    @Schema(description = "전체 상점 조회")
    private List<StoreResponse> stores;

    public StoresResponse(final List<StoreResponse> stores) {
        this.stores = stores;
    }

}
