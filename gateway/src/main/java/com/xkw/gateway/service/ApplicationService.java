package com.xkw.gateway.service;

import com.xkw.gateway.domain.Application;
import com.xkw.gateway.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author wangwei
 * @since 1.0
 */
@Service
public class ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Cacheable(value = "application", key = "#id")
    public Application getById(String id) {
        return applicationRepository.findById(id).orElse(null);
    }
}
