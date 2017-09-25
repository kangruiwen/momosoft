package main.ehcache.develop.basic;

import net.sf.ehcache.CacheManager;

/**
 * @author momo
 * @time 2017年9月19日下午2:11:45
 * 
 * singleton and instance 
 * 
 */
public class EhcacheManagerUtil {
	
	/**
	 * create  a singleton CacheManager using defaults
	 */
	public static void createSingletonManager() {
		CacheManager manager = CacheManager.create(); 
		String[] cacheNames1 = CacheManager.getInstance().getCacheNames();
	}
	
	/**
	 * creates a CacheManager instance using defaults
	 */
	public static void createInstanceManager() {
		CacheManager manager = CacheManager.newInstance(); 
		String[] cacheNames2 = manager.getCacheNames();
	}
	
	/**
	 * creates two CacheManagers, each with a different configuration,
	 */
	public static void createManagerUseConfig() {
		CacheManager manager1 = CacheManager.newInstance("src/config/ehcache1.xml"); 
		CacheManager manager2 = CacheManager.newInstance("src/config/ehcache2.xml"); 
		String[] cacheNamesForManager1 = manager1.getCacheNames(); 
		String[] cacheNamesForManager2 = manager2.getCacheNames();
	}
	
}
