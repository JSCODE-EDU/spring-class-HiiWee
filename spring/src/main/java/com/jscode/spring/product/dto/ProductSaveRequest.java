package com.jscode.spring.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductSaveRequest {

    @Schema(description = "이름", example = "상품 이름", required = true)
    @NotBlank(message = "이름은 반드시 한 글자 이상이어야 합니다.")
    private String name;

    @Schema(description = "가격", example = "10000", required = true)
    @NotNull(message = "가격은 반드시 입력해야 합니다.")
    private Long price;

    @Schema(description = "상점 id", example = "1", required = true)
    @NotNull(message = "상품에 대한 상점을 반드시 등록해야 합니다.")
    private Long storeId;

    private ProductSaveRequest() {
    }

    @Builder
    public ProductSaveRequest(final String name, final Long price, final Long storeId) {
        this.name = name;
        this.price = price;
        this.storeId = storeId;
    }


}
