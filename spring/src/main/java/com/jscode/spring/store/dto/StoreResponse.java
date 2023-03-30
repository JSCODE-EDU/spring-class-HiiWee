package com.jscode.spring.store.dto;

import com.jscode.spring.store.domain.Address;
import com.jscode.spring.store.domain.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreResponse {

    @Schema(description = "상점 id", example = "1")
    private Long id;

    @Schema(description = "상점 이름", example = "등록 상점 이름")
    private String name;

    @Schema(description = "상점 전화번호", example = "010-0000-0000")
    private String phone;

    @Schema(description = "상점 주소")
    private Address address;

    @Builder
    private StoreResponse(final Long id, final String name, final String phone, final Address address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public static StoreResponse from(final Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .phone(store.getPhone())
                .address(store.getAddress())
                .build();
    }

}

