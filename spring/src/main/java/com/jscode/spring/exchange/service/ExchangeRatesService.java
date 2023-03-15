package com.jscode.spring.exchange.service;

import com.jscode.spring.exchange.config.ExchangeYamlRead;
import com.jscode.spring.exchange.domain.ExchangeRates;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRatesService {

    private final ExchangeYamlRead exchangeYamlRead;

    public ExchangeRatesService(final ExchangeYamlRead exchangeYamlRead) {
        this.exchangeYamlRead = exchangeYamlRead;
    }

    public double convertKrwToUsd(final int krw) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<ExchangeRates> exchange = template.exchange(
                "https://api.apilayer.com/exchangerates_data/latest?symbols=KRW&base=USD",
                HttpMethod.GET,
                new HttpEntity<>(generateHeader("apikey")),
                ExchangeRates.class
        );
        return krw / exchange.getBody()
                .findRates("KRW");
    }

    private HttpHeaders generateHeader(final String header) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(header, exchangeYamlRead.getKey());
        return httpHeaders;
    }

}
