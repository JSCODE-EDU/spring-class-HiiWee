package com.jscode.spring.product.dto;

import com.jscode.spring.product.domain.Product;
import java.util.Objects;
import lombok.Getter;

@Getter
public class ProductResponse {

    private Long id;
    private String name;
    private double price;

    private ProductResponse(final Long id, final String name, final double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(final Product product, final double price) {
        return new ProductResponse(product.getId(), product.getName(), price);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductResponse that = (ProductResponse) o;
        return Double.compare(that.price, price) == 0 && id.equals(that.id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

}
