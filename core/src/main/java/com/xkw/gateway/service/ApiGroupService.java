package com.xkw.gateway.service;

import com.xkw.gateway.dao.ApiGroupRepository;
import com.xkw.gateway.dao.PermissionRepository;
import com.xkw.gateway.domain.ApiGroup;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangwei
 * @since 1.0
 */
@Service
public class ApiGroupService {

    @Autowired
    ApiGroupRepository apiGroupRepository;
    @Autowired
    PermissionRepository permissionRepository;

    public List<ApiGroup> getAll() {
        return IteratorUtils.toList(apiGroupRepository.findAll().iterator());
    }

    @Cacheable(value = "api-group", key = "#id", unless = "#result == null")
    public ApiGroup getById(String id) {
        return apiGroupRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "api-group", key = "#id")
    public ApiGroup getCache(String id) {

        //        return applicationRepository.findById(id).orElse(null);
        return null;
    }

    @CacheEvict(value = "api-group", key = "#apiGroup.id")
    public void save(ApiGroup apiGroup) {
        apiGroupRepository.save(apiGroup);
    }

    public void delete(String id) {
        apiGroupRepository.deleteById(id);
    }


}
