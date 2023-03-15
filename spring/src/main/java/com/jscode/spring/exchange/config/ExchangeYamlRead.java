package com.jscode.spring.exchange.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:exchanges.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "apilayer")
@Getter
@Setter
public class ExchangeYamlRead {

    private String key;

}
