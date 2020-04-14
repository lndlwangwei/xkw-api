package com.xkw.gateway.model;

import com.alibaba.fastjson.JSON;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.net.URI;
import java.time.LocalDateTime;

@Entity
public class GatewayRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String serviceId;

    private String uri;

    private String predicates;

    private String filters;

    private int order;

    private LocalDateTime createDate;

    private int status;

    public RouteDefinition buildRouteDefinition() {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(this.serviceId);
        routeDefinition.setUri(URI.create(this.uri));
        routeDefinition.setFilters(JSON.parseArray(this.filters, FilterDefinition.class));
        routeDefinition.setPredicates(JSON.parseArray(this.predicates, PredicateDefinition.class));
        routeDefinition.setOrder(this.order);

        return routeDefinition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPredicates() {
        return predicates;
    }

    public void setPredicates(String predicates) {
        this.predicates = predicates;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
