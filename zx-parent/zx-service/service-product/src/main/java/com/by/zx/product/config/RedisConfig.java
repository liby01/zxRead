package com.by.zx.product.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration // 标记这是一个配置类
public class RedisConfig {

    @Bean // 定义一个 Bean，用于配置缓存管理器
    public CacheManager cacheManager(LettuceConnectionFactory connectionFactory) {

        // 定义值序列化器，使用 GenericJackson2JsonRedisSerializer 将对象序列化为 JSON
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        // 定义键序列化器，使用 StringRedisSerializer 将字符串序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // 配置 Redis 缓存配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 设置缓存过期时间为 600 秒
                .entryTtl(Duration.ofSeconds(600))
                // 配置键的序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
                // 配置值的序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer));

        // 创建 Redis 缓存管理器
        RedisCacheManager cacheManager = RedisCacheManager.builder(connectionFactory)
                // 设置默认的缓存配置
                .cacheDefaults(config)
                .build();

        // 返回缓存管理器
        return cacheManager;
    }
}