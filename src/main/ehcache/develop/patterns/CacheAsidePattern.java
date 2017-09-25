package main.ehcache.develop.patterns;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * @author momo
 * @time 2017年9月20日下午12:57:20
 * 
 * 官方文档给出的一个cache-aside的一个栗子
 * 
 * cache-aside :
 * 读操作：检查缓存中存在不存在，存在了直接读，不存在从源读，然后更新缓存
 * 写操作：（感觉这点和网上说的不太一样）官网上是这么介绍的：
 * When data is writen, the cache must be updated with the system-of-record.
 * 既如果是更新，则需要先更新源，然后再更新缓存
 * 
 * 但是网上公认的cache-aside写操作基本上都是：
 * 	1. 修改的数据保存到数据区，
 * 	2. 使缓存区的对应项无效。
 *  个人感觉这个更合理，因为有并发操作时，可以防止脏数据的产生
 * 
 */
public class CacheAsidePattern {
	
	 private final Ehcache cache; 
	 
	 public CacheAsidePattern(Ehcache cache) { 
		 this.cache = cache; 
	 } 
	 
	 /* read some data, check cache first, otherwise read from sor */ 
	 @SuppressWarnings("deprecation")
	public Object readSomeData(String key) {
		 Element element; 
		 Object value;
		 if ((element = cache.get(key)) != null) { 
			 return element.getValue(); 
		 }
		 // note here you should decide whether your cache 
		 // will cache 'nulls' or not 
		 if ((value=readDataFromDataStore(key)) != null) { 
			 cache.put(new Element(key, value)); 
		 } 
		 return value; 
	 }
	 
	 /* write some data, write to sor, then update cache */ 
	 public void writeSomeData(String key, Object value) {
		 writeDataToDataStore(key, value); 
		 cache.put(new Element(key, value)); 
	 }
	 
	 public void writeDataToDataStore(String key,Object value) {
		 //..
	 }
	 
	 public Object readDataFromDataStore(String key) {
		 return null;
		 //...
	 }
}
