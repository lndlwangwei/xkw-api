package com.xkw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

// todo gateway 路由监控和动态配置  actuator
@SpringBootApplication
@RestController
public class GatewayApplication {

    private static final Logger log = LoggerFactory.getLogger(GatewayApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

//    @Bean
//    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//            .route(p -> p
//                .path("/get")
//                .filters(f -> f.addRequestHeader("Hello", "World"))
//                .uri("https://www.baidu.com"))
//            .build();
//    }

    @Bean
    KeyResolver customKeyResolver() {
        return exchange -> {
            String hostname = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName();
            System.out.println(hostname);
            return Mono.just(hostname);
        };
    }

    @RequestMapping("bar")
    public String testRewritePath() {
        return "bar";
    }

    @Bean
    @Order(-1)
    public GlobalFilter a() {
        return (exchange, chain) -> {
            log.info("first pre filter");

            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            ServerHttpResponse response = exchange.getResponse();

            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap("this is a error request".getBytes());
            response.writeWith(Flux.just(buffer));

            return Mono.empty();

//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//                log.info("third post filter");
//            }));
        };
    }

    @Bean
    @Order(0)
    public GlobalFilter b() {
        return (exchange, chain) -> {
            log.info("second pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("second post filter");
            }));
        };
    }

    @Bean
    @Order(1)
    public GlobalFilter c() {
        return (exchange, chain) -> {
            log.info("third pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("first post filter");
                System.out.println(exchange.getRequest().getPath());
            }));
        };
    }
}
