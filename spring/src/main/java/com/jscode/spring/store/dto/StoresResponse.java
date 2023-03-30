package com.jscode.spring.store.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class StoresResponse {

    private List<StoreResponse> stores;

    public StoresResponse(final List<StoreResponse> stores) {
        this.stores = stores;
    }

}
