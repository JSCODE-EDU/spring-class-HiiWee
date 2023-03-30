package com.jscode.spring.config;

import com.jscode.spring.advice.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ApiResponses({
        @ApiResponse(responseCode = StatusCode.OK, description = "API 호출 성공"),

        @ApiResponse(
                responseCode = StatusCode.BAD_REQUEST,
                description = "유효하지 않은 리소스를 통한 요청",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),

        @ApiResponse(
                responseCode = StatusCode.NOT_FOUND,
                description = "존재하지 않는 리소스에 대한 요청",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),

        @ApiResponse(
                responseCode = StatusCode.INTERNAL_SERVER_ERROR,
                description = "서버에서 발생한 예상치 못한 오류",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
})
public @interface ApiDocumentResponse {
}
