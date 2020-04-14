package com.xkw.gateway.bean;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.xkw.support.cache.redis.JsonRedisTemplate;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author wangwei
 * @since 1.0
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        // 使用FastJsonRedisSerializer会报
        GenericFastJsonRedisSerializer fastJsonRedisSerializer =
                new GenericFastJsonRedisSerializer();

        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config =
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer)).disableCachingNullValues();

        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();

        return cacheManager;
    }

    @Bean
    public JsonRedisTemplate jsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new JsonRedisTemplate(redisConnectionFactory);
    }
}
