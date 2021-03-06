package com.xkw;

import com.netflix.discovery.DiscoveryManager;
import com.xkw.gateway.auth.GatewayAuthentication;
import com.xkw.gateway.auth.GatewayAuthorization;
import com.xkw.gateway.common.GatewayException;
import com.xkw.gateway.service.GatewayRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
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
    GatewayAuthentication gatewayAuthentication;
    @Autowired
    GatewayAuthorization gatewayAuthorization;
    @Autowired
    GatewayRouteService gatewayRouteService;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

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

    @GetMapping("offline")
    public void unregisterFromEurekaServer() {
        DiscoveryManager.getInstance().shutdownComponent();
    }

    @Bean
    @Order(-1)
    public GlobalFilter a() {

        return (exchange, chain) -> {

            try {
                String appId = gatewayAuthentication.doJwtAuthc(exchange.getRequest());
                gatewayAuthorization.doAuthorization(appId, exchange.getRequest());

                // 授权通过后，想header中添加一个Proxy_Authorized的header
                ServerHttpRequest host = exchange.getRequest().mutate().header("Proxy_Authorized", "true").build();
                exchange.mutate().request(host).build();
            } catch (GatewayException e) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.FORBIDDEN);
                response.getHeaders().set("Content-Type", "text/plain;charset=UTF-8");

                byte[] bytes = e.getMessage().getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bytes);

                return response.writeWith(Flux.just(buffer));
            }

            return chain.filter(exchange);
        };
    }
}
