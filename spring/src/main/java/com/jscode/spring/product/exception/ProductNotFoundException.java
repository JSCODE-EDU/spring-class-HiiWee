package com.jscode.spring.product.exception;

import com.advice.BusinessException;

public class ProductNotFoundException extends BusinessException {

    private static final String MESSAGE = "존재하지 않는 상품입니다.";

    public ProductNotFoundException() {
        super(MESSAGE);
    }

}
