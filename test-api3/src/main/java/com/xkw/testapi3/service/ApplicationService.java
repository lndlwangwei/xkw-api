package com.xkw.testapi3.service;

import com.xkw.testapi3.domain.Application;
import com.xkw.testapi3.repository.ApplicationRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangwei
 * @since 1.0
 */
@Service
public class ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    public List<Application> getAll() {
        return IteratorUtils.toList(applicationRepository.findAll().iterator());
    }
}
