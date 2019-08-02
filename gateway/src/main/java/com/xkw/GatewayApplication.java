package com.xkw;

import com.netflix.discovery.DiscoveryManager;
import com.xkw.gateway.auth.BasicAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

            // 如果path是/mdm/courses, 那么apiName就是apiName
            String apiName =
                exchange.getRequest().getPath().pathWithinApplication().elements().get(1).value();

            return Mono.just(String.format("%s:%s", hostname, apiName));
        };
    }

    @Value("${server.port}")
    int port;
    @RequestMapping("bar")
    public String testRewritePath() throws UnknownHostException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        return String.format("bar %s %s", ip);
    }

    @GetMapping("offline")
    public void unregisterFromEurekaServer() {
        DiscoveryManager.getInstance().shutdownComponent();
    }

    @Bean
    @Order(-1)
    public GlobalFilter a() {

        return (exchange, chain) -> {

            //            try {
            //                basicAuth.doAuth(exchange.getRequest());
            //            } catch (GatewayException e) {
            //                ServerHttpResponse response = exchange.getResponse();
            //                response.setStatusCode(HttpStatus.FORBIDDEN);
            //                response.getHeaders().set("Content-Type", "text/plain;charset=UTF-8");
            //
            //                byte[] bytes = e.getMessage().getBytes(StandardCharsets.UTF_8);
            //                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            //
            //                return response.writeWith(Flux.just(buffer));
            //            }

            return chain.filter(exchange);
        };
    }
}
