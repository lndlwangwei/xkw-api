package com.xkw.gateway.dao;

import com.xkw.gateway.model.GatewayRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayRouteRepository extends JpaRepository<GatewayRoute, Integer> {
}
