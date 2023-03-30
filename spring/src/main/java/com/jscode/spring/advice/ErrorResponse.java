package com.jscode.spring.advice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ErrorResponse {

    @Schema(example = "오류 메시지")
    private String message;

    public ErrorResponse(final String message) {
        this.message = message;
    }

}
