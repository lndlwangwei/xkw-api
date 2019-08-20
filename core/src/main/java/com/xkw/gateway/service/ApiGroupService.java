package com.xkw.gateway.service;

import com.xkw.gateway.common.GatewayException;
import com.xkw.gateway.dao.ApiGroupRepository;
import com.xkw.gateway.dao.PermissionRepository;
import com.xkw.gateway.domain.ApiGroup;
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
public class ApiGroupService {

    @Autowired
    ApiGroupRepository applicationRepository;
    @Autowired
    PermissionRepository permissionRepository;

    public List<ApiGroup> getAll() {
        return IteratorUtils.toList(applicationRepository.findAll().iterator());
    }

    @Cacheable(value = "api-group", key = "#id")
    public ApiGroup getById(String id) {
        return applicationRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "api-group", key = "#id")
    public ApiGroup getCache(String id) {

        //        return applicationRepository.findById(id).orElse(null);
        return null;
    }

    @CacheEvict(value = "api-group", key = "#apiGroup.id")
    public void save(ApiGroup apiGroup) {
        applicationRepository.save(apiGroup);
    }

    public void delete(String id) {
        applicationRepository.deleteById(id);
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
    public void updateAppPermission(String appId, List<String> permissionsStr) {
        permissionRepository.deleteByAppId(appId);
        if (!CollectionUtils.isEmpty(permissionsStr)) {
            List<Permission> permissions =
                permissionsStr.stream().filter(permissionStr -> !StringUtils.isEmpty(permissionStr)).map(permissionStr -> new Permission(appId, permissionStr)).collect(Collectors.toList());
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
}
