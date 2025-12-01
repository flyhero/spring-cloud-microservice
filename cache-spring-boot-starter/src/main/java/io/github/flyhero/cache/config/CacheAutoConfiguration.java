package io.github.flyhero.cache.config;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.github.flyhero.cache.MultiLevelCacheManager;

@AutoConfiguration
@ConditionalOnClass({Caffeine.class, RedisCacheManager.class})
@EnableConfigurationProperties(CacheProperties.class)
public class CacheAutoConfiguration {

	// ======================== 1. Caffeine缓存管理器（仅本地缓存） ========================
	@Bean
	@ConditionalOnMissingBean(name = "caffeineCacheManager")
	public CaffeineCacheManager caffeineCacheManager(CacheProperties properties) {
		CacheProperties.CaffeineProperties caffeineProps = properties.getCaffeine();
		Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
				.expireAfterWrite(caffeineProps.getExpireAfterWrite())
				.maximumSize(caffeineProps.getMaximumSize());
		CaffeineCacheManager manager = new CaffeineCacheManager();
		manager.setCaffeine(caffeine);
		return manager;
	}

	// ======================== 2. Redis缓存管理器（仅分布式缓存） ========================
	@Bean
	@ConditionalOnMissingBean(name = "redisCacheManager")
	public RedisCacheManager redisCacheManager(RedisConnectionFactory factory, CacheProperties properties) {
		CacheProperties.RedisProperties redisProps = properties.getRedis();
		// 配置支持 Java 8 时间类型的 ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		// 配置Jackson序列化器，添加类型信息, 类型信息作为属性（如@class:com.xxx.Product）
		objectMapper.activateDefaultTyping(
				BasicPolymorphicTypeValidator.builder().allowIfSubType(Object.class).build(),
				ObjectMapper.DefaultTyping.NON_FINAL,
				JsonTypeInfo.As.WRAPPER_ARRAY
		);
		// 改为使用 GenericJackson2JsonRedisSerializer，确保集合根类型以 WRAPPER_ARRAY 方式编码，读写一致
		GenericJackson2JsonRedisSerializer genericSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);

		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(redisProps.getTtl())
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericSerializer));
		return RedisCacheManager.builder(factory)
				.cacheDefaults(config)
				.build();
	}

	// ======================== 3. 多级缓存管理器（Caffeine + Redis） ========================
	@Bean
	@ConditionalOnMissingBean(name = "multiLevelCacheManager")
	public MultiLevelCacheManager multiLevelCacheManager(
			CaffeineCacheManager caffeineCacheManager,
			RedisCacheManager redisCacheManager,
			RedisConnectionFactory factory) {
		return new MultiLevelCacheManager(caffeineCacheManager, redisCacheManager, factory);
	}

	// ======================== 4. 默认CacheManager（根据type选择） ========================
	@Bean
	@Primary
//	@ConditionalOnMissingBean(CacheManager.class)
	public CacheManager defaultCacheManager(
			CacheProperties properties,
			CaffeineCacheManager caffeineCacheManager,
			RedisCacheManager redisCacheManager,
			MultiLevelCacheManager multiLevelCacheManager) {
		return switch (properties.getDefaultType()) {
			case CAFFEINE -> caffeineCacheManager;
			case REDIS -> redisCacheManager;
			case MULTI_LEVEL -> multiLevelCacheManager;
		};
	}
}