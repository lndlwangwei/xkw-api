package com.xkw;

import com.netflix.discovery.DiscoveryManager;
import com.netflix.discovery.guice.EurekaModule;
import com.xkw.utils.GracefulShutdown;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
@EnableEurekaClient
public class TestApi1Application {

    @Value("${server.port}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(TestApi1Application.class, args);
    }

//    @RequestMapping
//    public String test(HttpServletRequest request) {
//        System.out.println(request.getRequestURL());
//        return "this is api 1";
//    }

    @RequestMapping("/test-filters")
    public String testFilters(Map<String, String> params, @RequestHeader HttpHeaders header) {
        System.out.println(params);
        System.out.println(header);
        return params.toString();
    }

    @RequestMapping("/addedPath/**")
    public String testPrefixPath() {
        return "addedPath";
    }

    @RequestMapping("bar")
    public String testRewritePath() {
        return "bar";
    }

    @GetMapping("test-micro-service")
    public String testMicroService() throws InterruptedException {
        System.out.println("start sleeping");
//        Thread.sleep(30000);
        String response = String.format("response from %s", port);
        System.out.println("finish sleep!!");
        return response;
    }

    @GetMapping("offline")
    public boolean offline() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                DiscoveryManager.getInstance().shutdownComponent();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        return true;
    }

    @Bean
    public GracefulShutdown gracefulShutdown() {
        return new GracefulShutdown();
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory(final GracefulShutdown gracefulShutdown) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(gracefulShutdown);
        return factory;
    }
}
