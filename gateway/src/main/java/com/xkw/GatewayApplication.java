package com.xkw;

import com.xkw.auth.BasicAuth;
import com.xkw.common.GatewayException;
import org.apache.shiro.authz.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

// todo gateway 路由监控和动态配置  actuator
@SpringBootApplication
@RestController
@EnableCaching
public class GatewayApplication {

    private static final Logger log = LoggerFactory.getLogger(GatewayApplication.class);

    @Autowired
    BasicAuth basicAuth;

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

            try {
                basicAuth.doAuth(exchange.getRequest());
            } catch (GatewayException e) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.FORBIDDEN);
                response.getHeaders().set("Content-Type", "text/plain;charset=UTF-8");

                byte[] bytes = e.getMessage().getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

                return response.writeWith(Flux.just(buffer));
            }

            return chain.filter(exchange);
        };
    }
}
