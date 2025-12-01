package io.github.flyhero.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@ConfigurationProperties(prefix = "multilevel.cache")
public class CacheProperties {

	/**
	 * 缓存类型（默认多级缓存）
	 */
	private CacheType defaultType = CacheType.MULTI_LEVEL;

	/** Caffeine本地缓存配置 */
	private CaffeineProperties caffeine = new CaffeineProperties();

	/** Redis分布式缓存配置 */
	private RedisProperties redis = new RedisProperties();

	/**
	 * Caffeine配置子属性
	 */
	@Getter
	@Setter
	public static class CaffeineProperties {
		/**
		 * 写入后过期时间（默认5分钟）
		 */
		private Duration expireAfterWrite = Duration.ofMinutes(5);

		/**
		 * 最大缓存容量（默认1000）
		 */
		private long maximumSize = 1000;

	}

	/**
	 * Redis配置子属性
	 */
	@Getter
	@Setter
	public static class RedisProperties {
		/**
		 * 缓存TTL（默认10分钟）
		 */
		private Duration ttl = Duration.ofMinutes(10);
	}
}