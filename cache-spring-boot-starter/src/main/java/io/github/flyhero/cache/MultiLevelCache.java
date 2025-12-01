package io.github.flyhero.cache;

import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.Nullable;

/**
 * 多级缓存实现类（Caffeine + Redis）
 */
public class MultiLevelCache extends AbstractValueAdaptingCache {

	// 缓存名称
	private final String name;
	// Caffeine本地缓存
	private final Cache caffeineCache;
	// Redis分布式缓存
	private final RedisCache redisCache;
	// Redis连接工厂
	private final RedisConnectionFactory redisConnectionFactory;

	public MultiLevelCache(String name, Cache caffeineCache, RedisCache redisCache, RedisConnectionFactory redisConnectionFactory) {
		super(true);
		this.name = name;
		this.caffeineCache = caffeineCache;
		this.redisCache = redisCache;
		this.redisConnectionFactory = redisConnectionFactory;
	}

	// 判断Redis是否可用
	private boolean isRedisAvailable() {
		try {
			redisConnectionFactory.getConnection().ping();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Object getNativeCache() {
		return this; // 返回当前缓存实例
	}


	@Override
	@Nullable
	protected Object lookup(Object key) {
		// 1. 先查本地缓存
		Object value = caffeineCache.get(key, () -> null);
		if (value != null) {
			return value;
		}
		// 2. Redis不可用时，直接返回null（后续查库）
		if (!isRedisAvailable()) {
			return null;
		}
		// 3. 本地未命中，查Redis缓存
		value = redisCache.get(key, () -> null);
		if (value != null) {
			// 将Redis结果同步到本地缓存
			caffeineCache.put(key, value);
			return value;
		}

		return null;
	}

	@Override
	public void put(Object key, @Nullable Object value) {
		// 同时更新本地缓存和Redis
		caffeineCache.put(key, value);
		redisCache.put(key, value);
	}

	@Override
	public void evict(Object key) {
		// 同时删除本地缓存和Redis
		caffeineCache.evict(key);
		redisCache.evict(key);
	}

	@Override
	public void clear() {
		// 清空所有缓存
		caffeineCache.clear();
		redisCache.clear();
	}

	@Override
	@Nullable
	public <T> T get(Object key, Callable<T> valueLoader) {
		// 自定义加载逻辑（适配@Cacheable的sync=true场景）
		return (T) lookup(key);
	}
}