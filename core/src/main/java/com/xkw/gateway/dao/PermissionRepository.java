package com.xkw.gateway.dao;

import com.xkw.gateway.domain.Permission;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author wangwei
 * @since 1.0
 */
public interface PermissionRepository extends CrudRepository<Permission, Integer> {

    List<Permission> getByAppIdAndType(String appId, int type);

    int deleteByAppId(String appId);
}
