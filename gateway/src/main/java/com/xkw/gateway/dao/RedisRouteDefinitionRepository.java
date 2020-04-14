package com.xkw.gateway.dao;

import com.xkw.gateway.common.GatewayException;
import com.xkw.gateway.model.GatewayRoute;
import com.xkw.gateway.service.GatewayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    @Autowired
    private GatewayRouteService gatewayRouteService;

    @PostConstruct
    public void initGatewayRoute() {
        getRouteDefinitions();
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<GatewayRoute> routes = gatewayRouteService.getAll();
        if (CollectionUtils.isEmpty(routes)) {
            throw new GatewayException("没有可用的路由配置!");
        }

        List<RouteDefinition> routeDefinitions = routes.stream().map(route -> route.buildRouteDefinition()).collect(Collectors.toList());

        return Flux.fromIterable(routeDefinitions);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }

}
