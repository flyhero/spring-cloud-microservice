package io.github.flyhero.cache;

import java.util.Collection;
import java.util.Collections;

import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author flyhero
 */
public class MultiLevelCacheManager extends AbstractCacheManager {

	private final CaffeineCacheManager caffeineCacheManager;
	private final RedisCacheManager redisCacheManager;
	private final RedisConnectionFactory redisConnectionFactory;

	public MultiLevelCacheManager(CaffeineCacheManager caffeineCacheManager, RedisCacheManager redisCacheManager, RedisConnectionFactory redisConnectionFactory) {
		this.caffeineCacheManager = caffeineCacheManager;
		this.redisCacheManager = redisCacheManager;
		this.redisConnectionFactory = redisConnectionFactory;
	}

	@Override
	protected Collection<? extends Cache> loadCaches() {
		return Collections.emptyList();
	}

	@Override
	protected Cache getMissingCache(String name) {
		Cache caffeineCache = caffeineCacheManager.getCache(name);
		RedisCache redisCache = (RedisCache) redisCacheManager.getCache(name);
		// 若任一缓存不存在（理论上不会，因CacheManager会自动创建），返回存在的那个
		if (caffeineCache == null) {
			return redisCache;
		}
		if (redisCache == null) {
			return caffeineCache;
		}
		return new MultiLevelCache(name, caffeineCache, redisCache, redisConnectionFactory);
	}


	// 下面 CompositeCacheManager 不够灵活
/*	@Bean
	public CacheManager cacheManager(RedisTemplate redisTemplate) {
		CompositeCacheManager cacheManager = new CompositeCacheManager();

		// Caffeine缓存管理器
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
				.maximumSize(1000)
				.expireAfterWrite(10, TimeUnit.MINUTES));

		// Redis缓存管理器
		RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisTemplate.getConnectionFactory())
				.cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
						.entryTtl(Duration.ofMinutes(30)))
				.build();

		cacheManager.setCacheManagers(Arrays.asList(caffeineCacheManager, redisCacheManager));
		return cacheManager;
	}*/
}