package com.jscode.spring.advice;

public class BusinessException extends RuntimeException {

    public BusinessException(final String message) {
        super(message);
    }

}
