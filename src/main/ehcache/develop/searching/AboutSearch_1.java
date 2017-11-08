package main.ehcache.develop.searching;

import java.net.URL;
import java.util.List;

import main.ehcache.develop.basic.LoadConfigurationUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

/**
 * @author momo
 * @time 2017年9月27日下午12:35:11
 * 
 * Search API Recommend
 * 
 */
public class AboutSearch_1 {
	public static void main(String[] args) {
		URL url = LoadConfigurationUtil.class.getClass().getResource("/ehcache/searchEhcache.xml"); 
		CacheManager manager = CacheManager.newInstance(url);
		Cache cache = manager.getCache("testCache");
		Element element = new Element("key1", "value1"); 
		cache.put(element);
		element = new Element("key2", "value2"); 
		cache.put(element);
		element = new Element("key3", "value3"); 
		cache.put(element);
		
		Results results = cache.createQuery().addCriteria(Query.KEY.eq("key1")).execute(); 
		List<Result> ret = results.all();
		
		for(Result r : ret ) {
			System.out.println(r.getKey() + ":" + r.getValue());
		}
	}
}
