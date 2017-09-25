package main.ehcache.develop.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import net.sf.ehcache.CacheManager;

/**
 * @author momo
 * @time 2017年9月19日下午2:27:50
 * 
 * cacheManager创建过程中加载配置文件的几种不同方式
 */
public class LoadConfigurationUtil {
	
	/**
	 * creates a CacheManager based on the configuration defined in the ehcache.xml file in the classpath
	 */
	public static void loadByDefault() {
		CacheManager manager = CacheManager.newInstance();
	}
	
	/**
	 * creates a CacheManager based on a specified configuration file.
	 */
	public static void loadByAnyWhere() {
		CacheManager manager = CacheManager.newInstance("src/config/ehcache.xml");
	}
	
	/**
	 * creates a CacheManager from a configuration resource in the classpath
	 */
	public static void loadInClasspath() {
		URL url = LoadConfigurationUtil.class.getClass().getResource("/anotherconfigurationname.xml"); 
		CacheManager manager = CacheManager.newInstance(url);
	}
	
	public static void loadByIO() throws Exception {
		InputStream fis = new FileInputStream(new File("src/config/ehcache.xml").getAbsolutePath());
		try { 
			CacheManager manager = CacheManager.newInstance(fis); 
		} finally { 
			fis.close(); 

		}
	}
}
