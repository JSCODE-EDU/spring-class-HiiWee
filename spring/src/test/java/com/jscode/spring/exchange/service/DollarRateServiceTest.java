package com.jscode.spring.exchange.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DollarRateServiceTest {

    @Autowired
    ExchangeRatesService exchangeRatesService;

    @Test
    void convertKrwToUsd() {
        System.out.println(exchangeRatesService.convertKrwToUsd(10000));
    }
}