package main.ehcache.develop;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

/**
 * @author momo
 * @time 2017年9月19日下午2:48:37
 * 
 * 以编程的方式管理Cache
 */
public class ProgrammaticallyCRUDCache {
	
	/**
	 * 1. The cache is configured using defaultCache from the CacheManagerconfiguration
	 */
	public void addCache1() {
		CacheManager singletonManager = CacheManager.create(); 
		singletonManager.addCache("testCache"); 
		Cache test = singletonManager.getCache("testCache");
	}
	
	/**
	 *  2. create a new cache with a specified configuration and addthe cache to a CacheManager
	 */
	public void addCache2() {
		CacheManager singletonManager = CacheManager.create(); 
		Cache memoryOnlyCache = new Cache("testCache", 5000, false, false, 5, 2); 
		singletonManager.addCache(memoryOnlyCache); 
		Cache test = singletonManager.getCache("testCache");
	}
	
	/**
	 *  3. another way to create a new cache with a specified configuration and addthe cache to a CacheManager
	 */
	public void addCache3() {
		//Create a singleton CacheManager using defaults 
		CacheManager manager = CacheManager.create(); 
		//Create a Cache specifying its configuration. 
		Cache testCache = new Cache( 
			new CacheConfiguration("testCache", 1000/*maxEntriesLocalHeap*/) 
			.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU) 
			.eternal(false) 
			.timeToLiveSeconds(60) 
			.timeToIdleSeconds(30) 
			.diskExpiryThreadIntervalSeconds(0) 
			.persistence(new PersistenceConfiguration().strategy(Strategy.LOCALTEMPSWAP))
		  );
		manager.addCache(testCache);
	}
	
	/**
	 *  4. cache的移除
	 */
	public void removeCache() {
		CacheManager singletonManager = CacheManager.create(); 
		singletonManager.removeCache("sampleCache1");
	}
	
	/**
	 *  5. 对cache的一些基本操作
	 */
	public void basicOperCache() {
		
		CacheManager manager = CacheManager.create(); 
		
		//1. obtain cache
		Cache cache = manager.getCache("sampleCache1");
		
		//2. putting element in cache
		Element element = new Element("key1", "value1"); 
		cache.put(element);
		
		//3. Updating and Element in Cache
		cache.put(new Element("key1", "value2"));
		
		//4. Getting an Element from Cache
		Element retEle = cache.get("key1"); 
		Object value = retEle.getObjectValue();
		
		//5. remove an element from a cache
		cache.remove("key1");
		
		//6. Obtaining Cache Sizes
		// gets the number of elements currently in the cache.
		int elementsInMemory = cache.getSize();
	}
	
	
}
