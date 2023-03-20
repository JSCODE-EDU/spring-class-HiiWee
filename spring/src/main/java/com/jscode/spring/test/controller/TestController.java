package com.jscode.spring.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test() {
        log.info("call test() controller");
        return "test";
    }

    @GetMapping("/name")
    public String name() {
        log.info("call name() controller");
        return "이호석";
    }

    @GetMapping("/api/name")
    public String queryStringName(@RequestParam final String name) {
        log.info("call queryStringName() controller, param = {}", name);
        return name;
    }

}
