package com.jscode.spring.Store.dto;

import com.jscode.spring.Store.domain.Address;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreSaveRequest {

    private String name;
    private String phone;
    private Address address;

    private StoreSaveRequest() {
    }

    @Builder
    private StoreSaveRequest(final String name, final String phone, final Address address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
