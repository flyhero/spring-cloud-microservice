package io.github.flyhero.cache.config;

/**
 * 缓存类型枚举
 */
public enum CacheType {
	/**
	 * 多级缓存（Caffeine + Redis）
	 */
	MULTI_LEVEL,
	/**
	 * 仅本地缓存（Caffeine）
	 */
	CAFFEINE,
	/**
	 * 仅分布式缓存（Redis）
	 */
	REDIS
}
