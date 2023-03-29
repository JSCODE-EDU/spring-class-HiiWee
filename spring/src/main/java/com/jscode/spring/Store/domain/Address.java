package com.jscode.spring.Store.domain;

import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String street;
    private String detail;

    protected Address() {
    }

    public Address(final String street, final String detail) {
        this.street = street;
        this.detail = detail;
    }

}
