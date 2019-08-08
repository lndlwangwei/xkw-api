package com.xkw.testapi3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaClient
public class TestApi3Application {

    //    @Autowired
    //    RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(TestApi3Application.class, args);
    }

    //    @GetMapping("/master/**")
    //    public ResponseEntity<?> proxyPath(ProxyExchange<byte[]> proxy) throws Exception {
    //        String path = proxy.path("/");
    //        return proxy.uri("http://localhost:8081/" + path).get();
    //    }

    //    @Bean
    //    @LoadBalanced
    //    public RestTemplate restTemplate() {
    //        return new RestTemplate();
    //    }
    //
    //    @GetMapping("mdm/**")
    //    public String test() {
    //        return "this is mdm service";
    //    }
    //
    //    @GetMapping("test-service")
    //    public String testService() {
    //        return restTemplate.getForObject("http://xkw-api-gateway/bar", String.class);
    //    }
    //
    //    @RequestMapping
    //    public String api() {
    //        return restTemplate.getForObject("http://xkw-api-gateway/bar", String.class);
    //    }
}
