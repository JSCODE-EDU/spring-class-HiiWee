package com.jscode.spring.exchange.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.jscode.spring.product.domain.MonetaryUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DollarRateServiceTest {

    @Autowired
    ExchangeRatesService exchangeRatesService;

    @Test
    @DisplayName("KRW to KRW 변환 성공 테스트")
    void convertKrwToKrw_success() {
        double convertedPrice = exchangeRatesService.convertKrwTo(MonetaryUnit.KRW, 10000L);
        assertThat(convertedPrice).isEqualTo(10000.0);
    }

    @Test
    @DisplayName("KRW to USD 변환 성공 테스트")
    void convertKrwToUsd_success() {
        double convertedPrice = exchangeRatesService.convertKrwTo(MonetaryUnit.KRW, 10000L);
        assertThat(convertedPrice).isEqualTo(10000.0);
    }
}