package com.xkw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestApi2Application {

    public static void main(String[] args) {
        SpringApplication.run(TestApi2Application.class, args);
    }

    @RequestMapping
    public String test() {
        return "this is api 2";
    }
}
