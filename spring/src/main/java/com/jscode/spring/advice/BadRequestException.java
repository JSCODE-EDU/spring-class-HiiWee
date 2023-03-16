package com.jscode.spring.advice;

public class BadRequestException extends BusinessException {

    public BadRequestException(final String message) {
        super(message);
    }

}
