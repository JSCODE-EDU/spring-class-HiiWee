package com.jscode.spring.advice;

public class NotFoundException extends BusinessException {

    public NotFoundException(final String message) {
        super(message);
    }

}
