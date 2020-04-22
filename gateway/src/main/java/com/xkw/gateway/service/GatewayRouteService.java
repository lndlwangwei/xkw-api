package com.xkw.gateway.service;

import com.xkw.gateway.dao.GatewayRouteRepository;
import com.xkw.gateway.dao.RedisRouteDefinitionRepository;
import com.xkw.gateway.model.GatewayRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "gateway:route")
public class GatewayRouteService {

    @Autowired
    GatewayRouteRepository gatewayRouteRepository;
    @Autowired
    RedisRouteDefinitionRepository redisRouteDefinitionRepository;

    @Cacheable(key = "'allGatewayRoute'")
    public List<GatewayRoute> getAll() {
        return gatewayRouteRepository.findAll();
    }

    @CacheEvict(key = "'allGatewayRoute'")
    public void update(GatewayRoute gatewayRoute) {
        gatewayRouteRepository.save(gatewayRoute);
        redisRouteDefinitionRepository.getRouteDefinitions();
    }

    @CacheEvict(key = "'allGatewayRoute'")
    public void delete(GatewayRoute gatewayRoute) {
        gatewayRouteRepository.deleteById(gatewayRoute.getId());
        redisRouteDefinitionRepository.getRouteDefinitions();
    }
}
