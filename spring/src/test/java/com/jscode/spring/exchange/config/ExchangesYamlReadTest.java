package com.jscode.spring.exchange.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExchangesYamlReadTest {

    @Autowired
    ExchangeYamlRead exchangesYamlRead;

    @Test
    void getKey() {
        Assertions.assertThat(exchangesYamlRead.getKey()).isNotNull();
    }
}