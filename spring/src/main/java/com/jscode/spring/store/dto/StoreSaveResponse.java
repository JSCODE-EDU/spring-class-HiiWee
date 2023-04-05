package com.jscode.spring.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class StoreSaveResponse {

    @Schema(description = "저장된 상점 id", example = "1")
    private Long savedId;

    public StoreSaveResponse(final Long savedId) {
        this.savedId = savedId;
    }

}
