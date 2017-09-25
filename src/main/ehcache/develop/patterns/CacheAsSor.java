package main.ehcache.develop.patterns;

import java.util.Collection;

import net.sf.ehcache.CacheEntry;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.CacheEntryFactory;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;
import net.sf.ehcache.writer.CacheWriter;
import net.sf.ehcache.writer.writebehind.operations.SingleOperationType;

/**
 * @author momo
 * @time 2017年9月22日下午4:31:32
 * 
 * CacheAsSor Pattern:
 * 在说主题之前先普及几个概念：
 * 1.Write-through： 直写模式,在数据更新时,同时写入缓存Cache和后端存储。此模式的优点是操作简单；缺点是因为数据修改需要同时写入存储，数据写入速度较慢。
 * 2.Write-back(Write-behind):回写模式）在数据更新时只写入缓存Cache。只在数据被替换出缓存时，被修改的缓存数据才会被写到后端存储。
 * 		此模式的优点是数据写入速度快，因为不需要写存储；缺点是一旦更新后的数据未被写入存储时出现系统掉电的情况，数据将无法找回。
 * 3.Read-through:
 * 
 */
public class CacheAsSor<K,V> {
	
	private final Ehcache cache;
	
	public CacheAsSor(Ehcache cache) { 
	  cache.registerCacheWriter(new MyCacheWriter()); 
	  this.cache = new SelfPopulatingCache(cache,new MyCacheEntryFactory()); 
	} 

	/* read some data - notice the cache is treated as an SOR. 
	* the application code simply assumes the key will always be available 
	*/ 
	public Object readSomeData(String key) { 
		return cache.get(key); 
	} 
	
	/* write some data - notice the cache is treated as an SOR, it is 
	* the cache's responsibility to write the data to the SOR. 
	*/ 
	public void writeSomeData(String key, Object value) { 
		cache.put(new Element(key, value)); 
	} 
}
	
/** 
* Implement the CacheEntryFactory that allows the cache to provide 
* the read-through strategy 
*/
class MyCacheEntryFactory implements CacheEntryFactory { 
	public Object createEntry(Object key) throws Exception { 
		return new Object();
	} 
}


/** 
* Implement the CacheWriter interface which allows the cache to provide 
* the write-through or write-behind strategy. 
*/ 
class MyCacheWriter implements CacheWriter  {

  	public CacheWriter clone(Ehcache cache) throws CloneNotSupportedException  { 
		throw new CloneNotSupportedException();  
	} 

	public void init() { } 
  
	public void dispose() throws CacheException {} 
  
	public void write(Element element) throws CacheException{ 
		// writeDataToDataStore(element.getKey(), element.getValue()); 
	} 
  
	public void writeAll(Collection<Element> elements) throws CacheException { 
		for (Element element : elements) { 
			write(element); 
		} 
	} 
	
	public void delete(CacheEntry entry) throws CacheException { 
		//deleteDataFromDataStore(element.getKey()); 
  	} 
	
	public void deleteAll(Collection<CacheEntry> entries) throws CacheException { 
		//deleteAll	
	}

	public void throwAway(Element element, SingleOperationType operationType,
			RuntimeException e) {
		// TODO 
	} 
}
