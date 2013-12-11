package com.sklay.core.cache;

import net.sf.ehcache.Ehcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

public class SimpleEhCacheCacheManager extends EhCacheCacheManager {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SimpleEhCacheCacheManager.class);

	private static final String NO_CACHE_ERROR_MSG = "loadCaches must not return an empty Collection";

	@Override
	public void afterPropertiesSet() {
		try {
			super.afterPropertiesSet();
		} catch (IllegalArgumentException e) {
			if (NO_CACHE_ERROR_MSG.equals(e.getMessage())) {
				LOGGER.debug("No cache was defined in ehcache.xml. The error "
						+ "thrown by spring because of this was suppressed.");
			} else {
				throw e;
			}
		}
	}

	@Override
	public Cache getCache(String name) {
		Cache cache = super.getCache(name);
		if (cache == null) {
			LOGGER.debug("No cache with name '"
					+ name
					+ "' is defined in encache.xml. Hence creating the cache dynamically...");
			Ehcache ehcache = super.getCacheManager().addCacheIfAbsent(name);
			if (ehcache != null) {
				cache = new EhCacheCache(ehcache);
				addCache(cache);
			}
			LOGGER.debug("Cache with name '" + name
					+ "' is created dynamically...");
		}
		return cache;

	}

	public String[] getAllCacheNames() {

		return super.getCacheManager().getCacheNames();
	}

	public void remove(String cacheName) {

		if (super.getCacheManager().cacheExists(cacheName)) {
			super.getCacheManager().removeCache(cacheName);
			LOGGER.debug("CacheManager was cache  cacheName is {} .", cacheName);
		}
	}

	/**
	 * 把所有cache对象都删除。慎用！ Removes all caches using removeCache(String) for each
	 * cache.
	 */
	public void clearAll() {
		if (super.getCacheManager() != null) {
			super.getCacheManager().clearAll();
			LOGGER.debug("CacheManager was removalAll...");
		}
	}
}
