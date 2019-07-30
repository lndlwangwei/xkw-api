package com.xkw;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaClient
public class TestApi2Application {

    @Value("${server.port}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(TestApi2Application.class, args);
    }

//    @RequestMapping
//    public String test() {
//        return "this is api 2";
//    }

    @GetMapping("qbm/abc")
    public String testMicroService() throws InterruptedException {
        return String.format("this is qbm");
    }
}
