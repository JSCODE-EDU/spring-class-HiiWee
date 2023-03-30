package com.jscode.spring.store.dto;

import lombok.Getter;

@Getter
public class StoreSaveResponse {

    private Long savedId;

    public StoreSaveResponse(final Long savedId) {
        this.savedId = savedId;
    }

}
