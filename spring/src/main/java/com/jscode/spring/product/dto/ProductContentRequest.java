package com.jscode.spring.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductContentRequest {

    @Schema(description = "이름", example = "상품 이름", required = true)
    @NotBlank(message = "조회를 위한 이름 입력은 필수입니다.")
    private String name;

    @Schema(description = "가격", example = "상품 가격", required = true)
    @NotNull(message = "조회를 위한 가격 입력은 필수입니다.")
    private Long price;

    public ProductContentRequest() {
    }

    public ProductContentRequest(final String name, final Long price) {
        this.name = name;
        this.price = price;
    }
}
