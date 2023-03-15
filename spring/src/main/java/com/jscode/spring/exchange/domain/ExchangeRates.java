package com.jscode.spring.exchange.domain;

import java.time.LocalDate;
import java.util.Map;
import lombok.Getter;

@Getter
public class ExchangeRates {

    private String base;
    private LocalDate date;
    private Map<String, Double> rates;

    public ExchangeRates() {
    }

    public double findRates(final String countryName) {
        return rates.get(countryName);
    }

}
