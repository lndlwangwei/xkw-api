package com.xkw.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangwei
 * @since 1.0
 */
@Service
public class PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Cacheable(value = "Permission", key = "#appId")
    public List<Permission> getByAppId(String appId) {
        return permissionRepository.getByAppId(appId);
    }
}
