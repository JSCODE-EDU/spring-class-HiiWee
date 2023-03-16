package com.jscode.spring.product.exception;

import com.advice.BusinessException;

public class DuplicateNameException extends BusinessException {

    private static final String MESSAGE = "동일한 이름의 상품은 저장할 수 없습니다.";

    public DuplicateNameException() {
        super(MESSAGE);
    }

}
