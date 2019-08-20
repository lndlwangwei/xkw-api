package com.xkw.admin.bean;

import java.lang.annotation.*;

/**
 * @author wangwei
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@org.springframework.cache.annotation.Cacheable
public @interface Cacheable {

    Class type() default Object.class;
}
