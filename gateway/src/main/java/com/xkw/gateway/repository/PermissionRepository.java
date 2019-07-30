package com.xkw.gateway.repository;

import com.xkw.gateway.domain.Permission;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author wangwei
 * @since 1.0
 */
public interface PermissionRepository extends CrudRepository<Permission, Integer> {

    List<Permission> getByAppId(String appId);
}