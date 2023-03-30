package com.jscode.spring.Store.dto;

import com.jscode.spring.Store.domain.Address;
import com.jscode.spring.Store.domain.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreResponse {

    private Long id;
    private String name;
    private String phone;
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

