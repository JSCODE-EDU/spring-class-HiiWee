package com.jscode.spring.store.exception;

import com.jscode.spring.advice.NotFoundException;

public class StoreNotFoundException extends NotFoundException {

    private static final String MESSAGE = "상점을 찾을 수 없습니다.";

    public StoreNotFoundException() {
        super(MESSAGE);
    }

}
