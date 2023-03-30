package com.jscode.spring.store.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    @Schema(example = "도로명")
    private String street;

    @Schema(example = "상세주소")
    private String detail;

    protected Address() {
    }

    public Address(final String street, final String detail) {
        this.street = street;
        this.detail = detail;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(detail, address.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, detail);
    }

}
