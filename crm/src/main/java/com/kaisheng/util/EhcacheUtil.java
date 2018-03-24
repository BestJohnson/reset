package com.kaisheng.util;

import java.io.Serializable;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class EhcacheUtil {
	private static CacheManager cacheManager = new CacheManager();
	private String cacheName;
	private Ehcache cache;
	
	
	public EhcacheUtil(String cacheName) {
		this.cacheName = cacheName;
	}
	
	/**
	 * 获取缓存对象
	 * @return
	 */
	public Ehcache getEhcache() {
		if(cache == null) {
			cache = cacheManager.getEhcache(cacheName);
		}
		return cache;
	}
	
	/**
	 * 向缓存对象中存值
	 * @param key
	 * @param value
	 */
	public void setCache(Object key, Object value) {
		Element element = new Element(key,value);
		cache.put(element);
	}
	
	public void setCache(Serializable key, Serializable value) {
		Element element = new Element(key,value);
		getEhcache().put(element);
	}
	
	/**
	 * 从缓存对象中取值
	 * @param key
	 */
	public Object getCacheValue(Object key) {
		Element element = cache.get(key);
		return element == null ? null : element.getObjectValue();
	}
	public Object getCacheValue(Serializable key) {
		Element element = getEhcache().get(key);
		return element == null ? null : element.getObjectValue();
	}
	
	/**
	 * 从缓存中根据key值删除
	 * @param key
	 */
	public void removeByKey(Object key) {
		cache.remove(key);
	}
	public void removeByKey(Serializable key) {
		getEhcache().remove(key);
	}
	
	/**
	 * 删除缓存中全部
	 * @param cache
	 */
	public void removeAll(Ehcache cache) {
		cache.removeAll();
	}
	public void removeAll(Serializable cache) {
		getEhcache().removeAll();
	}
	
}
