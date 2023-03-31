package com.jscode.spring.store.dto;

import com.jscode.spring.store.domain.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreSaveRequest {

    @Schema(description = "상점 이름", example = "등록 상점 이름")
    @NotBlank(message = "상점 이름은 필수로 입력해야 합니다.")
    private String name;

    @Schema(description = "상점 전화 번호", example = "010-0000-0000")
    private String phone;

    @Schema(description = "상점 주소")
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
