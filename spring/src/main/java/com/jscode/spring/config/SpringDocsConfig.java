package com.jscode.spring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocsConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String version) {
        Info info = new Info();
        info.title("Spring Boot Basic Study")
                .version(version)
                .description("스프링 부트 스터디")
                .contact(new Contact()
                        .name("이호석")
                        .email("wpdlzhf159@gmail.com")
                        .url("https://github.com/HiiWee"));

        return new OpenAPI()
                .info(info);
    }

}
