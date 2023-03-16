package com.jscode.spring.exchange.service;

import com.jscode.spring.exchange.config.ExchangeYamlRead;
import com.jscode.spring.exchange.domain.ExchangeRates;
import com.jscode.spring.product.domain.MonetaryUnit;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRatesService {

    public static final String EXCHANGE_URL = "https://api.apilayer.com/exchangerates_data/latest?symbols=KRW&base=%s";
    private final ExchangeYamlRead exchangeYamlRead;

    public ExchangeRatesService(final ExchangeYamlRead exchangeYamlRead) {
        this.exchangeYamlRead = exchangeYamlRead;
    }

    public double convertKrwTo(final MonetaryUnit monetaryUnit, final int value) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<ExchangeRates> exchange = template.exchange(
                String.format(EXCHANGE_URL, monetaryUnit.toString()),
                HttpMethod.GET,
                new HttpEntity<>(generateHeader("apikey")),
                ExchangeRates.class
        );
        return value / exchange.getBody()
                .findRates("KRW");
    }

    private HttpHeaders generateHeader(final String header) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(header, exchangeYamlRead.getKey());
        return httpHeaders;
    }

}
