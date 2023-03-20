package com.jscode.spring.product.exception;

import com.jscode.spring.advice.NotFoundException;

public class ProductNotFoundException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 상품입니다.";

    public ProductNotFoundException() {
        super(MESSAGE);
    }

}
