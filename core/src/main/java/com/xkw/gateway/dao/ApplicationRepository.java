package com.xkw.gateway.dao;

import com.xkw.gateway.domain.Application;
import org.springframework.data.repository.CrudRepository;

/**
 * @author wangwei
 * @since 1.0
 */
public interface ApplicationRepository extends CrudRepository<Application, String> {
}
