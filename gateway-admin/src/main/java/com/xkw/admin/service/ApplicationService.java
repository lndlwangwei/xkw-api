package com.xkw.admin.service;

import com.xkw.admin.domain.Application;
import com.xkw.admin.domain.Permission;
import com.xkw.admin.repository.ApplicationRepository;
import com.xkw.admin.repository.PermissionRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<Application> getAll() {
        return IteratorUtils.toList(applicationRepository.findAll().iterator());
    }

    public void save(Application application) {
        applicationRepository.save(application);
    }

    public void delete(String id) {
        applicationRepository.deleteById(id);
    }

    public List<Permission> getAppPermission(String appId) {
        return permissionRepository.getByAppId(appId);
    }

    @Transactional
    public void updateAppPermission(String appId, List<String> permissionsStr) {
        permissionRepository.deleteByAppId(appId);
        if (!CollectionUtils.isEmpty(permissionsStr)) {
            List<Permission> permissions =
                permissionsStr.stream().map(permissionStr -> new Permission(appId, permissionStr)).collect(Collectors.toList());
            permissionRepository.saveAll(permissions);
        }
    }
}
