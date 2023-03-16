package com.jscode.spring.product.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ProductListResponse {

    private List<ProductResponse> productResponses;

    public ProductListResponse(final List<ProductResponse> productResponses) {
        this.productResponses = productResponses;
    }

}
