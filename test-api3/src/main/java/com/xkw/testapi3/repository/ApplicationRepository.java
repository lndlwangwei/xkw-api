package com.xkw.testapi3.repository;

import com.xkw.testapi3.domain.Application;
import org.springframework.data.repository.CrudRepository;

/**
 * @author wangwei
 * @since 1.0
 */
public interface ApplicationRepository extends CrudRepository<Application, String> {
}
