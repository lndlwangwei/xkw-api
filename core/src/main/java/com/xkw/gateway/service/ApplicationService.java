package com.xkw.gateway.service;

import com.xkw.gateway.common.GatewayException;
import com.xkw.gateway.dao.ApplicationRepository;
import com.xkw.gateway.dao.PermissionRepository;
import com.xkw.gateway.domain.Application;
import com.xkw.gateway.domain.Permission;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wangwei
 * @since 1.0
 */
@Service
public class ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    PermissionRepository permissionRepository;

    @Cacheable(value = "applications", key = "'all'")
    public List<Application> getAll() {
        return IteratorUtils.toList(applicationRepository.findAll().iterator());
    }

    @CacheEvict(value = "applications", key = "'all'")
    public void save(Application application) {
        applicationRepository.save(application);
    }

    public void delete(String appId) {
        applicationRepository.deleteById(appId);
    }

    @Cacheable(value = "api-permission", key = "#groupId")
    public List<Permission> getAppPermission(String groupId, int type) {
        List<Permission> permissions = permissionRepository.getByAppIdAndType(groupId, type);
        if (!CollectionUtils.isEmpty(permissions)) {
            permissions.forEach(permission -> parseApiPermission(permission));
        }

        return permissions;
    }

    @CacheEvict(value = "api-permission", key = "#appId")
    @Transactional
    public void updateAppPermission(String appId, String groupId, List<String> permissionsStr) {
        permissionRepository.deleteByAppIdAndGroupId(appId, groupId);
        if (!CollectionUtils.isEmpty(permissionsStr)) {
            List<Permission> permissions =
                permissionsStr.stream().filter(permissionStr -> !StringUtils.isEmpty(permissionStr)).map(permissionStr -> new Permission(appId, groupId, permissionStr)).collect(Collectors.toList());
            permissionRepository.saveAll(permissions);
        }
    }

    public void parseApiPermission(Permission permission) {
        if (permission == null || StringUtils.isEmpty(permission.getPermission()))
            return;

        try {
            Set<String> supportedMethods = new HashSet<>();
            int index = permission.getPermission().indexOf(':');
            if (index < 0) {
                return;
            }

            permission.setApiPermissionPath(permission.getPermission().substring(0, index));

            String methodsStr = permission.getPermission().substring(index + 1);
            permission.setApiPermissionMethod(Arrays.asList(methodsStr.split("\\s*,\\s*")));
        } catch (Exception e) {
            throw new GatewayException("Api权限表达式不符合要求，解析出错");
        }
    }

    @Cacheable(value = "application", key = "#appId")
    public Application getById(String appId) {
        return this.getAll().stream().filter(application -> application.getId().equals(appId)).findFirst().orElse(null);
    }
}
